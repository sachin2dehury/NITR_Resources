package github.sachin2dehury.nitrresources.component

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import github.sachin2dehury.nitrresources.R

object AppDialog {
    fun layoutDialog(context: Context, layOut: Int): AlertDialog.Builder {
        AlertDialog.Builder(context).apply {
            setView(layOut)
            create()
            show()
            return this
        }
    }

    fun logOutDialog(context: Context, action: String) {
        AlertDialog.Builder(context).apply {
            setView(R.layout.fragment_rename)
            setTitle(action)
            setMessage("Are you sure to Log Out?")
            setIcon(R.drawable.ic_baseline_delete_sweep_24)
            setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->

            }
            setNeutralButton("No") { dialogInterface: DialogInterface, i: Int ->

            }
            create()
            show()
        }
    }

    fun defaultDialog(context: Context, action: String) {
        AlertDialog.Builder(context).apply {
            setView(R.layout.fragment_rename)
            setTitle(action)
            setMessage("Are you sure to $action this item?")
            setIcon(R.drawable.ic_baseline_delete_sweep_24)
            setPositiveButton(action) { dialogInterface: DialogInterface, i: Int ->

            }
            setNeutralButton("Cancel") { dialogInterface: DialogInterface, i: Int ->

            }
            create()
            show()
        }
    }
}