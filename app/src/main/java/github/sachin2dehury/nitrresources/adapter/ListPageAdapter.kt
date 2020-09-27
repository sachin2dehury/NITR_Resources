package github.sachin2dehury.nitrresources.adapter

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppItemAction
import github.sachin2dehury.nitrresources.component.AppLogic
import github.sachin2dehury.nitrresources.component.AppMenu
import github.sachin2dehury.nitrresources.core.DocDetails
import github.sachin2dehury.nitrresources.viewholder.ListPageViewHolder
import kotlinx.android.synthetic.main.list_item.view.*

class ListPageAdapter(private val item: Int, private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<ListPageViewHolder>(), Filterable {

    private val list = AppLogic.pageSelector(item)
    private var listData = list
    private val keys = listData.keys

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListPageViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListPageViewHolder, position: Int) {
        val current = keys.elementAt(position)
        val doc = list[current]!!
        val img = when (AppCore.mime.getExtensionFromMimeType(doc.type)) {
            "pdf" -> R.drawable.ic_baseline_picture_as_pdf_24
            "jpg", "jpeg", "png" -> R.drawable.ic_baseline_image_24
            "ppt", "doc" -> R.drawable.ic_baseline_slideshow_24
            else -> R.drawable.ic_baseline_warning_24
        }
        holder.itemView.apply {
            image.setImageResource(img)
            name.text = doc.subjectName
            subject.visibility = View.VISIBLE
            size.visibility = View.VISIBLE
            menuButton.visibility = View.VISIBLE
            name.setLines(1)
            size.text = "${AppCore.format.format(doc.size)} MB"
            subject.text = doc.courseName
            if (doc.contributor == AppCore.firebaseAuth.currentUser!!.email!!) {
                isMine.visibility = View.VISIBLE
            }
            setOnClickListener {
                Toast.makeText(context, "Downloading ${doc.subjectName}", Toast.LENGTH_SHORT).show()
                AppItemAction.downloadDoc(doc, context)
            }
            menuButton.setOnClickListener {
                notifyDataSetChanged()
                val menu = PopupMenu(context, it, Gravity.END).apply {
                    menuInflater.inflate(R.menu.item_menu, menu)
                    animate()
                    AppMenu.getMenuIcon(this)
                    show()
                }
                menu.setOnMenuItemClickListener { menuItem ->
                    AppMenu.popUpMenu(menuItem, context, current, item, doc)
                    true
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(value: CharSequence?): FilterResults {
                val search = value.toString().toLowerCase()
                val filterResults = FilterResults()
                filterResults.values = if (search.isEmpty()) {
                    list
                } else {
                    list.filter {
                        it.value.courseName.toLowerCase().contains(search) ||
                                it.value.courseName.toLowerCase().contains(search)
                    }
                }
                return filterResults
            }

            override fun publishResults(value: CharSequence?, filterResults: FilterResults?) {
                listData = (filterResults!!.values as LinkedHashMap<String, DocDetails>)
                notifyDataSetChanged()
            }
        }
    }
}