package github.sachin2dehury.nitrresources.fragment

import android.os.Bundle
import android.view.Menu
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppPreference

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        setHasOptionsMenu(true)

        val email =
            PreferenceManager.getDefaultSharedPreferences(context).getString("Email", "Trash")!!
        val streamArray = AppCore.streamList.toTypedArray()
        val password =
            PreferenceManager.getDefaultSharedPreferences(context).getString("Password", "Trash")!!
        findPreference<Preference>("Email")!!.apply {
            summary = email
        }
//        findPreference<EditTextPreference>("Password")!!.apply {
//            summary = "********"
//            text = password
//        }
        findPreference<ListPreference>("Stream")!!.apply {
            summary = AppCore.currentStream
            entries = streamArray
            entryValues = streamArray
            setValueIndex(AppCore.streamList.indexOf(AppCore.currentStream))
        }
    }

    override fun onStop() {
        super.onStop()
        AppPreference.saveAppData(requireContext())
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.removeItem(R.id.settings)
        menu.removeItem(R.id.user)
        super.onPrepareOptionsMenu(menu)
    }
}