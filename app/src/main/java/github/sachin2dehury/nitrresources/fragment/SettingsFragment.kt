package github.sachin2dehury.nitrresources.fragment

import android.os.Bundle
import android.view.MenuItem
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.core.Core
import github.sachin2dehury.nitrresources.core.streams

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val email = Core.firebaseAuth.currentUser!!.email!!
        findPreference<Preference>("email")!!.apply {
            summary = email
        }
        findPreference<EditTextPreference>("password")!!.apply {

        }
        findPreference<ListPreference>("Stream")!!.apply {
            entries = streams.toTypedArray()
        }
        findPreference<ListPreference>("Year")!!.apply {

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user -> Core.firebaseAuth.signOut()
        }
        return true
    }
}