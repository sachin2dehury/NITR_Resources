package github.sachin2dehury.nitrresources.dialog

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import github.sachin2dehury.nitrresources.R
import kotlinx.android.synthetic.main.dialog_action.*

class ActionDialog(context: Context, private val action: String) : AppCompatDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature((Window.FEATURE_NO_TITLE))
        setContentView(R.layout.dialog_action)
        actionHeader.text = action
        actionButton.text = action

        when (action) {
            "Delete" -> {
                actionIcon.setImageResource(R.drawable.ic_baseline_delete_sweep_24)
                actionButton.setOnClickListener {
                    dismiss()
                }
                actionCancelButton.setOnClickListener {
                    dismiss()
                }
            }
            "Log Out" -> {
                actionIcon.setImageResource(R.drawable.ic_baseline_account_circle_24)
                actionButton.setOnClickListener {
                    dismiss()
                }
                actionCancelButton.setOnClickListener {
                    dismiss()
                }
            }
            else -> {
                actionIcon.setImageResource(R.drawable.ic_baseline_warning_24)
                actionButton.setOnClickListener {
                    dismiss()
                }
                actionCancelButton.setOnClickListener {
                    dismiss()
                }
            }
        }
        setCancelable(true)
    }
}