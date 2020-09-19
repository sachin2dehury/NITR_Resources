package github.sachin2dehury.nitrresources.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.adapter.ListPageAdapter
import github.sachin2dehury.nitrresources.core.Core
import github.sachin2dehury.nitrresources.core.NOTES_LIST
import kotlinx.android.synthetic.main.page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageFragment(private val position: Int) : Fragment(R.layout.page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.VISIBLE
        listView.apply {
            adapter = ListPageAdapter(position, childFragmentManager)
            layoutManager = LinearLayoutManager(context)
        }
        Core.getList(NOTES_LIST + position).invokeOnCompletion {
            jobValidator(it)
        }

    }

    private fun jobValidator(throwable: Throwable?) = CoroutineScope(Dispatchers.Main).launch {
        progressBar.visibility = View.GONE
        if (throwable != null) {
            errorText.visibility = View.VISIBLE
            errorText.text = throwable.toString()
            Toast.makeText(context, throwable.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}