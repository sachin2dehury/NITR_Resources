package github.sachin2dehury.nitrresources.component

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.activity.NavActivity

object AppScreen {
    fun changeActivity(
        context: Context,
        file: String,
        rename: Boolean,
        pageIndex: Int = 0
    ) {
        val intent = Intent(context, NavActivity::class.java).apply {
            putExtra("Login", false)
            putExtra("File", file)
            putExtra("Rename", rename)
            putExtra("PageIndex", pageIndex)
        }
        context.startActivity(intent)
    }

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