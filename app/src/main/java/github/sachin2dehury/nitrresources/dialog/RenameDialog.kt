package github.sachin2dehury.nitrresources.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppItemAction
import github.sachin2dehury.nitrresources.component.AppJobs
import github.sachin2dehury.nitrresources.component.AppLogic
import github.sachin2dehury.nitrresources.core.DocDetails
import kotlinx.android.synthetic.main.dialog_rename.*

class RenameDialog(
    context: Context, private val files: ArrayList<String>,
    private val rename: Boolean = false,
    private val index: Int = 0
) : AppCompatDialog(context) {

    companion object {
        lateinit var doc: DocDetails
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_rename)

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
        saveButton.setOnClickListener {
            if (isValidFile()) {
                save()
            }
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
        setCancelable(false)
    }

    private fun save() {
        val item = spinnerPages.selectedItemPosition
        if (rename) {
            AppItemAction.renameDoc(files.first(), doc, index)
            Toast.makeText(context, "${doc.name} File being Renamed.", Toast.LENGTH_SHORT)
                .show()
        } else {
//            val intent = Intent(context, AppUploadService::class.java).apply {
//                putStringArrayListExtra("Files", files)
//                putExtra("Document", doc.toString())
//                putExtra("Index", item)
//            }
//            context.startService(intent)
            AppJobs.uploadDoc(files, doc, item)
            Toast.makeText(context, "${doc.name} File(s) being Uploaded.", Toast.LENGTH_SHORT)
                .show()
        }
        dismiss()
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
                    AppLogic.pageSelector(index)[files.first()]!!.copy(
                        name = fileName,
                        subCode = subCode
                    )
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