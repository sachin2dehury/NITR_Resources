package github.sachin2dehury.nitrresources.dialog

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.FragmentManager
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppNav
import github.sachin2dehury.nitrresources.fragment.LoginFragment
import kotlinx.android.synthetic.main.dialog_action.*

class LogOutDialog(
    context: Context,
    private val fragmentManager: FragmentManager
) : AppCompatDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val action = "Log Out"
        supportRequestWindowFeature((Window.FEATURE_NO_TITLE))
        setContentView(R.layout.dialog_action)
        actionHeader.text = action
        actionCancelButton.text = action
        actionIcon.setImageResource(R.drawable.ic_baseline_delete_sweep_24)
        actionCancelButton.setOnClickListener {
            dismiss()
        }
        actionButton.setOnClickListener {
            signOut()
            dismiss()
        }
        setCancelable(true)
    }

    private fun signOut() {
        when (AppCore.firebaseAuth.currentUser) {
            null -> AppNav.changeFragment(LoginFragment(), fragmentManager)
            else -> AppCore.firebaseAuth.signOut()
        }
    }
}