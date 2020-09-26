package github.sachin2dehury.nitrresources.component

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import github.sachin2dehury.nitrresources.R

object AppScreen {
    fun changeFragment(
        fragment: Fragment,
        fragmentManager: FragmentManager,
        nonEmpty: Boolean = true
    ) {
        fragmentManager.beginTransaction().apply {
            replace(R.id.navFragment, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            if (nonEmpty) {
                addToBackStack(fragment.javaClass.simpleName)
            }
            commit()
        }
    }
}