package github.sachin2dehury.nitrresources.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.core.pages
import kotlinx.android.synthetic.main.page.view.*

class PageAdapter : RecyclerView.Adapter<PageAdapter.PageHolder>() {
    inner class PageHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.page, parent, false)
        return PageHolder(view)
    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        holder.itemView.apply {
            listView.apply {
                adapter = ListPageAdapter(position)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun getItemCount(): Int {
        return pages.size
    }
}