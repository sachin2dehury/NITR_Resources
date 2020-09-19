package github.sachin2dehury.nitrresources.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.core.Core
import github.sachin2dehury.nitrresources.core.NO_LIST
import github.sachin2dehury.nitrresources.core.YEAR_LIST
import github.sachin2dehury.nitrresources.fragment.ListFragment
import github.sachin2dehury.nitrresources.fragment.TabFragment
import kotlinx.android.synthetic.main.list_item.view.*

class ListAdapter(private val item: Int, private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>(), Filterable {
    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val list = Core.listSelector(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
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
            setOnClickListener {
                val newItem = Core.listPredictor(item, position)
                Core.dataSetter(item, position)
                when (item) {
                    YEAR_LIST -> Core.changeFragment(TabFragment(), fragmentManager)
                    else -> Core.changeFragment(ListFragment(newItem), fragmentManager)
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(value: CharSequence?): FilterResults {
                Log.w("Test", "1")
                val search = value.toString()
                val filterResults = FilterResults()
                Log.w("Test", "2")
                filterResults.values = if (search.isEmpty()) {
                    list
                } else {
                    list.filter {
                        it.contentEquals(search)
                    }
                }
                Log.w("Test", "3")
                return filterResults
            }

            override fun publishResults(value: CharSequence?, filterResults: FilterResults?) {
                Log.w("Test", "4")
                filterResults!!.values as MutableSet<*>
                Log.w("Test", "5")
                notifyDataSetChanged()
            }
        }
    }
}