package com.example.signup.ui.preferences

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.signup.utils.Locator
import com.moronlu18.invoice.R

import kotlinx.coroutines.runBlocking

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
        initPreferencesInvoice()
        initPreferencesItem()
        //preferenceManager.preferenceDataStore = Locator.settingsPreferencesRepository


    }


    private fun initPreferencesInvoice() {
        val option = preferenceManager.findPreference<Preference>(getString(R.string.key_ivoice_order)) as ListPreference?

        option?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference){
                val index = preference.findIndexOfValue(newValue.toString())
                val entry = preference.entries.get(index)
                val entryvalue = preference.entryValues.get(index)
                println("----------------------- ${entryvalue} ")
                Locator.userPreferencesRepository.saveInvoiceOr(entryvalue.toString())
            }
            true
        }


    }

    private fun initPreferencesItem() {
        findPreference<ListPreference>(getString(R.string.key_item_order))?.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
                if (preference is ListPreference) {

                    val index = preference.findIndexOfValue(newValue.toString())

                    val entryValue = preference.entryValues.getOrNull(index)

                    entryValue?.let {
                        Locator.userPreferencesRepository.saveItemOrder(it.toString())
                    }

                }

                true
            }
        }
    }



}