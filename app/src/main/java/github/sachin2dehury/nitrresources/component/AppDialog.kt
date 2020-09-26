package github.sachin2dehury.nitrresources.component

import android.content.Context
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.FragmentManager
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.dialog.RenameDialog

object AppDialog {
    fun aboutDialog(context: Context) {
        AppCompatDialog(context).apply {
            supportRequestWindowFeature((Window.FEATURE_NO_TITLE))
            setContentView(R.layout.dialog_about)
            setCancelable(true)
            show()
        }
    }

    fun logoutDialog(fragmentManager: FragmentManager, context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle("Log Out")
            setMessage("Are you sure?")
            setIcon(R.drawable.ic_baseline_delete_sweep_24)
            setPositiveButton("Log Out") { dialog, _ ->
                AppAuth.signOut(fragmentManager)
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(true)
            show()
        }
    }

    fun renameDialog(docId: String, index: Int, context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle("Rename Item")
            setMessage("Are you sure?")
            setIcon(R.drawable.ic_baseline_delete_sweep_24)
            setPositiveButton("Rename") { dialog, _ ->
                RenameDialog(context, docId, true, index)
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(true)
            show()
        }
    }

    fun deleteDialog(docId: String, item: Int, context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle("Delete Item")
            setMessage("Are you sure?")
            setIcon(R.drawable.ic_baseline_delete_sweep_24)
            setPositiveButton("Delete") { dialog, _ ->
                AppItemAction.deleteDoc(docId, item)
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(true)
            show()
        }
    }
}