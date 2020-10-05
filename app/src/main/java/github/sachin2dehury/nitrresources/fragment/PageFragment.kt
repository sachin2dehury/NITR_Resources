package github.sachin2dehury.nitrresources.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.adapter.ListPageAdapter
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppJobs
import github.sachin2dehury.nitrresources.dialog.ActionDialog
import kotlinx.android.synthetic.main.fragment_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageFragment(private val position: Int) : Fragment(R.layout.fragment_page) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (AppCore.firebaseAuth.currentUser != null) {
            progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                AppJobs.getList(position).invokeOnCompletion {
                    jobValidator(it)
                }
                AppJobs.updateDocList(position, listView.adapter!!)
            }
        } else {
            val error = "Please Log in!"
            ActionDialog(requireContext(), error).show()
//            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
        listView.apply {
            adapter = ListPageAdapter(position, parentFragmentManager)
            layoutManager = LinearLayoutManager(context)
        }
        val adRequest = AdRequest.Builder().build()!!
        adView.loadAd(adRequest)

    }

    private fun jobValidator(throwable: Throwable?) = CoroutineScope(Dispatchers.Main).launch {
        progressBar.visibility = View.GONE
        when (throwable) {
            null -> isEmpty()
            else -> {
                val error = throwable.toString()
                ActionDialog(requireContext(), error).show()
//                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isEmpty() {
        if (listView.adapter!!.itemCount == 0) {
            val error = AppCore.noList.first()
            ActionDialog(requireContext(), error).show()
//            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.removeItem(R.id.searchBar)
        menu.removeItem(R.id.user)
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