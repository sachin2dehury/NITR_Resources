package github.sachin2dehury.nitrresources.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.core.Core
import github.sachin2dehury.nitrresources.core.DocDetails
import github.sachin2dehury.nitrresources.core.branch
import github.sachin2dehury.nitrresources.core.pages
import kotlinx.android.synthetic.main.fragment_rename.*

class RenameFragment(
    private val file: String,
    private val rename: Boolean = false,
    private val index: Int = 0
) : Fragment(R.layout.fragment_rename) {

    lateinit var doc: DocDetails

    @SuppressLint("SetTextI18n")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (rename) {
            saveButton.text = "Rename"
            renameHeader.text = "Rename File"
            spinnerPages.visibility = View.GONE
            spinnerBranch.visibility = View.GONE
        }
        renameHeader.text = "Save File"

        spinnerPages.apply {
            animate()
            adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, pages)
        }

        spinnerBranch.apply {
            animate()
            adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, branch)
        }

        cancelButton.setOnClickListener {
            childFragmentManager.popBackStack()
        }

        saveButton.setOnClickListener {
            if (isValidFile()) {
                save()
            }
        }
    }

    private fun save() {
        val item = spinnerPages.selectedItemPosition
        if (rename) {
            Core.renameDoc(file, doc, index)
            Toast.makeText(context, "${doc.name} File Renamed.", Toast.LENGTH_SHORT)
                .show()
        } else {
            Core.uploadDoc(Uri.parse(file), doc, item)
            Toast.makeText(context, "${doc.name} File being Uploaded.", Toast.LENGTH_SHORT).show()
        }
        childFragmentManager.popBackStack()
    }

    private fun isValidFile(): Boolean {
        val fileName = fileName.text.toString()
        val subCode = subCode.text.toString().toInt()
        val subName = spinnerBranch.selectedItem.toString()
        return when {
            fileName.isBlank() && fileName.length < 5 -> {
                Toast.makeText(context, "Please Enter Valid File Name", Toast.LENGTH_LONG).show()
                false
            }
            subCode in 1000..7000 -> {
                doc = DocDetails(fileName, subCode, subName)
                true
            }
            else -> {
                Toast.makeText(context, "Please Enter Valid Subject Code", Toast.LENGTH_LONG).show()
                false
            }
        }
    }
}