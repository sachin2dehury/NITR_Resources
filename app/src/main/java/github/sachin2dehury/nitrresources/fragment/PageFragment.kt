package github.sachin2dehury.nitrresources.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.adapter.ListPageAdapter
import github.sachin2dehury.nitrresources.core.Core
import kotlinx.android.synthetic.main.fragment_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageFragment(private val position: Int) : Fragment(R.layout.fragment_page) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            Core.getList(position).invokeOnCompletion {
                jobValidator(it)
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.VISIBLE
        listView.apply {
            adapter = ListPageAdapter(position, parentFragmentManager)
            layoutManager = LinearLayoutManager(context)
        }
        val adRequest = AdRequest.Builder().build()!!
        adView.loadAd(adRequest)
    }

    private fun jobValidator(throwable: Throwable?) = CoroutineScope(Dispatchers.Main).launch {
        progressBar.visibility = View.GONE
        if (throwable != null) {
            errorText.visibility = View.VISIBLE
            errorText.text = throwable.toString()
            Toast.makeText(context, throwable.toString(), Toast.LENGTH_SHORT).show()
        }
        listView.adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.searchBar).actionView as SearchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val adapter = listView.adapter as ListPageAdapter
                adapter.filter.filter(newText)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}