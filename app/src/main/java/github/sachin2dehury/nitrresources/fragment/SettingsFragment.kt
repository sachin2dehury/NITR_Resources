package github.sachin2dehury.nitrresources.fragment

import android.os.Bundle
import androidx.preference.*
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppPreference

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val email =
            PreferenceManager.getDefaultSharedPreferences(context).getString("Email", "Trash")!!
        val streamArray = AppCore.streamList.toTypedArray()
        val password =
            PreferenceManager.getDefaultSharedPreferences(context).getString("Password", "Trash")!!
        findPreference<Preference>("Email")!!.apply {
            summary = email
        }
        findPreference<EditTextPreference>("Password")!!.apply {
            summary = "********"
            text = password
        }
        findPreference<ListPreference>("Stream")!!.apply {
            summary = AppCore.stream
            entries = streamArray
            entryValues = streamArray
            setValueIndex(AppCore.streamList.indexOf(AppCore.stream))
        }
    }

    override fun onStop() {
        super.onStop()
        AppPreference.saveAppData(requireContext())
    }
}