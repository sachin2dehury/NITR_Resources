package github.sachin2dehury.nitrresources.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.core.Core
import github.sachin2dehury.nitrresources.core.IMG
import github.sachin2dehury.nitrresources.core.PDF
import github.sachin2dehury.nitrresources.core.format
import kotlinx.android.synthetic.main.items.view.*

class ListPageAdapter(private val item: Int) :
    RecyclerView.Adapter<ListPageAdapter.ListPageViewHolder>(), Filterable {
    inner class ListPageViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val list = Core.pageSelector(item)
    private val keys = list.keys

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return ListPageViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListPageViewHolder, position: Int) {
        val current = keys.elementAt(position)
        val doc = list[current]!!
        val img = when (doc.type) {
            PDF -> R.drawable.ic_baseline_picture_as_pdf_24
            IMG -> R.drawable.ic_baseline_image_24
            else -> R.drawable.ic_baseline_warning_24
        }
        holder.itemView.apply {
            image.setImageResource(img)
            name.text = doc.name
            subject.visibility = View.VISIBLE
            size.visibility = View.VISIBLE
            name.setLines(1)
            size.text = "${format.format(doc.size)} MB"
            subject.text = doc.subName + doc.subCode
            setOnClickListener {
                Core.openLink(doc.url, context)
            }
            setOnLongClickListener {
                val menu = PopupMenu(context, holder.itemView, Gravity.END).apply {
                    menuInflater.inflate(R.menu.item_menu, menu)
                    animate()
                    Core.getMenuIcon(this)
                    show()
                }
                menu.setOnMenuItemClickListener { menuItem ->
                    Core.popUpMenu(menuItem, context, current, item)
                    true
                }
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return keys.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(value: CharSequence?): FilterResults {
                Log.w("Test", "1")
                val search = value.toString()
                val filterResults = FilterResults()
                Log.w("Test", "2")
                filterResults.values = if (search.isEmpty())
                    keys
                else
                    keys.filter {
                        list[it]!!.name.contentEquals(search)
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