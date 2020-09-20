package github.sachin2dehury.nitrresources.adapter

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.core.Core
import github.sachin2dehury.nitrresources.core.IMG
import github.sachin2dehury.nitrresources.core.PDF
import github.sachin2dehury.nitrresources.core.format
import github.sachin2dehury.nitrresources.viewholder.ListPageViewHolder
import kotlinx.android.synthetic.main.list_item.view.*

class ListPageAdapter(private val item: Int, private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<ListPageViewHolder>() {

    private val list = Core.pageSelector(item)
    private val keys = list.keys

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
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
                    Core.popUpMenu(menuItem, context, current, item, fragmentManager)
                    true
                }
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return keys.size
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(value: CharSequence?): FilterResults {
//                val search = value.toString().toLowerCase()
//                val filterResults = FilterResults()
//                filterResults.values = if (search.isEmpty()) {
//                    keys
//                } else {
//                    keys.filter {
//                        list[it]!!.name.toLowerCase().contentEquals(search)
//                    }
//                }
//                return filterResults
//            }
//
//            override fun publishResults(value: CharSequence?, filterResults: FilterResults?) {
//                keys.clear()
//                val array = ArrayList<String>()
//                for (x in filterResults!!.values as ArrayList<*>) {
//                    array.add(x.toString())
//                }
//                keys.addAll(array)
//                notifyDataSetChanged()
//            }
//        }
//    }
}