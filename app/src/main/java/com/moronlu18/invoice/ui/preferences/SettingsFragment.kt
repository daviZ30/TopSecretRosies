package com.example.signup.ui.preferences

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.moronlu18.invoice.R

import kotlinx.coroutines.runBlocking

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)

        //preferenceManager.preferenceDataStore = Locator.settingsPreferencesRepository


    }
}