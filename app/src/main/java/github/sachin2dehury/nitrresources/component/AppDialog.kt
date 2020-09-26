package github.sachin2dehury.nitrresources.component

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import github.sachin2dehury.nitrresources.R

object AppDialog {
    fun logoutDialog(fragmentManager: FragmentManager, context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle("Log Out")
            setMessage("Are you sure?")
            setIcon(R.drawable.ic_baseline_delete_sweep_24)
            setPositiveButton("Log Out") { dialog, _ ->

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