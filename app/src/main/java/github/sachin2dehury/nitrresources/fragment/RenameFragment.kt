package github.sachin2dehury.nitrresources.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppItemAction
import github.sachin2dehury.nitrresources.component.AppLogic
import github.sachin2dehury.nitrresources.component.Upload
import github.sachin2dehury.nitrresources.core.DocDetails
import kotlinx.android.synthetic.main.fragment_rename.*

class RenameFragment(
    private val file: String,
    private val rename: Boolean = false,
    private val index: Int = 0
) : Fragment(R.layout.fragment_rename) {

    private lateinit var doc: DocDetails

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (rename) {
            saveButton.text = "Rename"
            renameHeader.text = "Rename File"
            spinnerPages.visibility = View.GONE
            spinnerBranch.visibility = View.GONE
        }

        spinnerPages.apply {
            animate()
            adapter =
                ArrayAdapter(
                    context,
                    R.layout.support_simple_spinner_dropdown_item,
                    AppCore.pageList
                )
        }

        spinnerBranch.apply {
            animate()
            adapter =
                ArrayAdapter(
                    context,
                    R.layout.support_simple_spinner_dropdown_item,
                    AppCore.branchList
                )
        }

        cancelButton.setOnClickListener {
            closeActivity()
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
            AppItemAction.renameDoc(file, doc, index)
            Toast.makeText(context, "${doc.name} File being Renamed.", Toast.LENGTH_SHORT)
                .show()
        } else {
            Upload.uploadDoc(Uri.parse(file), doc, item)
            Toast.makeText(context, "${doc.name} File being Uploaded.", Toast.LENGTH_SHORT).show()
        }
        closeActivity()
    }

    private fun closeActivity() {
        parentFragmentManager.popBackStack()
        requireActivity().finish()
    }

    private fun isValidFile(): Boolean {
        val fileName = fileName.text.toString()
        val subCode = subCode.text.toString().toInt()
        return when {
            fileName.isBlank() && fileName.length < 5 -> {
                Toast.makeText(context, "Please Enter Valid File Name", Toast.LENGTH_LONG).show()
                false
            }
            subCode in 1000..7000 -> {
                doc = if (rename) {
                    AppLogic.pageSelector(index)[file]!!.copy(name = fileName, subCode = subCode)
                } else {
                    val subName = spinnerBranch.selectedItem.toString()
                    DocDetails(fileName, subCode, subName)
                }
                true
            }
            else -> {
                Toast.makeText(context, "Please Enter Valid Subject Code", Toast.LENGTH_LONG).show()
                false
            }
        }
    }
}