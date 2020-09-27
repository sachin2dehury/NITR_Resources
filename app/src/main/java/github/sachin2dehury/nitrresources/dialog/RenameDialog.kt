package github.sachin2dehury.nitrresources.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppItemAction
import github.sachin2dehury.nitrresources.component.AppLogic
import github.sachin2dehury.nitrresources.core.DocDetails
import github.sachin2dehury.nitrresources.service.AppUploadService
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
            Toast.makeText(context, "${doc.courseName} File being Renamed.", Toast.LENGTH_SHORT)
                .show()
        } else {
            val intent = Intent(context, AppUploadService::class.java).apply {
                putStringArrayListExtra("Files", files)
                putExtra("Index", item)
            }
            context.startService(intent)
            Toast.makeText(context, "${doc.courseName} File(s) being Uploaded.", Toast.LENGTH_SHORT)
                .show()
        }
        dismiss()
    }

    private fun isValidFile(): Boolean {
        val subject = subjectName.text.toString()
        val course = courseName.text.toString()
        return when {
            subject.isBlank() || subject.length < 5 -> {
                Toast.makeText(context, "Please Enter Valid Subject Name", Toast.LENGTH_LONG).show()
                false
            }
            course.isBlank() || course.length < 5 -> {
                Toast.makeText(context, "Please Enter Valid Course Name", Toast.LENGTH_LONG).show()
                false
            }
            else -> {
                doc = if (rename) {
                    AppLogic.pageSelector(index)[files.first()]!!.copy(
                        subjectName = subject,
                        courseName = course
                    )
                } else {
                    DocDetails(subject, course)
                }
                true
            }
        }
    }
}