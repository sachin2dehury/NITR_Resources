package github.sachin2dehury.nitrresources.component

import androidx.fragment.app.FragmentManager
import github.sachin2dehury.nitrresources.fragment.LoginFragment

object AppAuth {
    fun signOut(fragmentManager: FragmentManager) {
        when (AppCore.firebaseAuth.currentUser) {
            null -> AppScreen.changeFragment(LoginFragment(), fragmentManager)
            else -> AppCore.firebaseAuth.signOut()
        }
    }
}