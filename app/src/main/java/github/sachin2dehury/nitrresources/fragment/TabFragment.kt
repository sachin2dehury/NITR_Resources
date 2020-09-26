package github.sachin2dehury.nitrresources.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.adapter.TabAdapter
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppJobs
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
            }
            startActivityForResult(intent, AppCore.REQUEST_CODE_OPEN_FILE)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                AppJobs.updateDocList(tab!!.position)
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppCore.REQUEST_CODE_OPEN_FILE && resultCode == RESULT_OK) {
            RenameDialog(requireContext(), data!!.data.toString(), false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppJobs.clearList()
    }
}