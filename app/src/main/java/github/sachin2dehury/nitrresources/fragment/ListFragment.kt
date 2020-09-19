package github.sachin2dehury.nitrresources.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.adapter.ListAdapter
import kotlinx.android.synthetic.main.fragment_page.*

class ListFragment(private val item: Int) : Fragment(R.layout.fragment_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView.apply {
            adapter = ListAdapter(item,parentFragmentManager)
            layoutManager = LinearLayoutManager(context)
        }
    }
}