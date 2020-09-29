package github.sachin2dehury.nitrresources.component

import android.content.Context
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.core.DocDetails
import github.sachin2dehury.nitrresources.dialog.AboutDialog
import github.sachin2dehury.nitrresources.dialog.ActionDialog
import github.sachin2dehury.nitrresources.dialog.LogOutDialog
import github.sachin2dehury.nitrresources.dialog.RenameDialog
import github.sachin2dehury.nitrresources.fragment.ListFragment
import github.sachin2dehury.nitrresources.fragment.LoginFragment
import github.sachin2dehury.nitrresources.fragment.SettingsFragment
import kotlin.system.exitProcess

object AppMenu {
    fun getMenuIcon(menu: PopupMenu) {
        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(menu)!!
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)
        } catch (e: Exception) {
            Log.w("Catch", e.toString())
        }
    }

    fun navDrawerMenu(item: MenuItem, context: Context, fragmentManager: FragmentManager) {
        when (item.itemId) {
            R.id.book -> AppItemAction.openLink(AppCore.BOOK_LINK, context)
            R.id.nitris -> AppItemAction.openLink(AppCore.QUESTION_LINK, context)
            R.id.mail -> AppItemAction.openLink(AppCore.MAIL_LINK, context)
            R.id.news -> AppItemAction.openLink(AppCore.TELEGRAM_NEWS_LINK, context)
            R.id.home -> {
                if (AppCore.firebaseAuth.currentUser == null) {
                    Toast.makeText(context, "Please Log in First!", Toast.LENGTH_LONG).show()
                    val index = fragmentManager.backStackEntryCount - 1
                    if (index >= 0 && fragmentManager.getBackStackEntryAt(index).javaClass != LoginFragment::javaClass) {
                        AppNav.changeFragment(LoginFragment(), fragmentManager)
                    }
                } else {
                    AppNav.changeFragment(
                        ListFragment(AppCore.STREAM_LIST),
                        fragmentManager
                    )
                }
            }
        }
    }

    fun optionMenu(item: MenuItem, context: Context, fragmentManager: FragmentManager) {
        when (item.itemId) {
            R.id.settings -> AppNav.changeFragment(SettingsFragment(), fragmentManager)
            R.id.about -> AboutDialog(context).show()
            R.id.exit -> exitProcess(0)
            R.id.user -> {
                when (AppCore.firebaseAuth.currentUser) {
                    null -> AppNav.changeFragment(LoginFragment(), fragmentManager)
                    else -> LogOutDialog(context).show()
                }
            }
        }
    }

    fun popUpMenu(
        item: MenuItem,
        context: Context,
        current: String,
        index: Int,
        doc: DocDetails
    ) {
        when (item.itemId) {
            R.id.rename -> RenameDialog(context, arrayListOf(current), true, index).show()
            R.id.delete -> ActionDialog(context, "Delete", current, index).show()
            R.id.download -> AppItemAction.downloadDoc(doc, context)
            R.id.share -> AppItemAction.shareDoc(doc, context)
        }
    }
}