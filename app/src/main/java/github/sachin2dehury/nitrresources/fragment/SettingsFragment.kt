package github.sachin2dehury.nitrresources.fragment

import android.os.Bundle
import androidx.preference.*
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.core.Core
import github.sachin2dehury.nitrresources.core.streams

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val email = Core.firebaseAuth.currentUser!!.email!!
        val streamsArray = streams.toTypedArray()
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
            summary = Core.stream
            entries = streamsArray
            entryValues = streamsArray
            setValueIndex(streams.indexOf(Core.stream))
        }
    }

    override fun onStop() {
        super.onStop()
        Core.saveAppData(requireContext())
    }
}