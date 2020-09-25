package github.sachin2dehury.nitrresources.component

import android.content.Context
import androidx.appcompat.app.AlertDialog
import github.sachin2dehury.nitrresources.R

object AppDialog {
    fun saveDialog(context: Context): AlertDialog.Builder {
        AlertDialog.Builder(context).apply {
            setView(R.layout.fragment_rename)
            setCancelable(false)
            return this
        }
    }

    fun defaultDialog(context: Context, action: String) {
        AlertDialog.Builder(context).apply {
            setView(R.layout.fragment_rename)
            setTitle(action)
            setMessage("Are you sure to $action this item?")
            setIcon(R.drawable.ic_baseline_delete_sweep_24)
            setPositiveButton(action) { dialog, position ->

            }
            setNeutralButton("Cancel") { dialog, position ->

            }
            create()
            show()
        }
    }
}