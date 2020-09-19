package github.sachin2dehury.nitrresources.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.core.Core
import github.sachin2dehury.nitrresources.core.NO_LIST
import github.sachin2dehury.nitrresources.core.YEAR_LIST
import github.sachin2dehury.nitrresources.fragment.ListFragment
import github.sachin2dehury.nitrresources.fragment.TabFragment
import kotlinx.android.synthetic.main.items.view.*

class ListAdapter(private val item: Int) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val list = Core.listSelector(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemView.apply {
            name.text = list[position]
            if (item == NO_LIST) {
                subject.visibility = View.VISIBLE
                size.visibility = View.VISIBLE
                name.setLines(1)
                size.text = "No Data Available!"
                subject.text = "No Data Available!"
            }
            val newItem = Core.listPredictor(item, position)
            setOnClickListener {
                Core.dataSetter(item, position)
                when (item) {
                    YEAR_LIST -> Core.changeFragment(TabFragment())
                    else -> Core.changeFragment(ListFragment(newItem))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (item == YEAR_LIST) {
            return Core.streamYr
        }
        return list.size
    }
}