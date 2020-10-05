package github.sachin2dehury.nitrresources.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.adapter.TabAdapter
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppLogic
import github.sachin2dehury.nitrresources.dialog.RenameDialog
import kotlinx.android.synthetic.main.fragment_tab.*

class TabFragment : Fragment(R.layout.fragment_tab) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = TabAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = AppCore.pageList[position]
        }.attach()

        uploadButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = AppCore.ALL
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            startActivityForResult(intent, AppCore.REQUEST_CODE_OPEN_FILE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppCore.REQUEST_CODE_OPEN_FILE && resultCode == RESULT_OK) {
            val list = data!!.clipData
            val files = ArrayList<String>()
            if (list != null) {
                for (item in 0 until list.itemCount) {
                    files.add(list.getItemAt(item).uri.toString())
                }
            } else {
                files.add(data.data.toString())
            }
            RenameDialog(requireContext(), files, false).show()
        }
    }

    override fun onDestroy() {
        AppCore.listener.remove()
        AppCore.commonListener.remove()
        AppLogic.clearList()
        super.onDestroy()
    }
}