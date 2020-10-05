package github.sachin2dehury.nitrresources.dialog

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import kotlinx.android.synthetic.main.dialog_action.*

class LogOutDialog(context: Context) : AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val action = "Log Out"
        supportRequestWindowFeature((Window.FEATURE_NO_TITLE))
        setContentView(R.layout.dialog_action)

        actionHeader.text = action
        actionButton.text = action

        actionIcon.setImageResource(R.drawable.ic_baseline_account_circle_24)

        actionCancelButton.setOnClickListener {
            dismiss()
        }
        actionButton.setOnClickListener {
            if (AppCore.remove) {
                AppCore.listener.remove()
                AppCore.commonListener.remove()
            }
            AppCore.firebaseAuth.signOut()
            dismiss()
        }
        setCancelable(true)
    }
}