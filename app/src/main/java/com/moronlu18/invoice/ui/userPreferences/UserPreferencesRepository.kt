package com.example.signup.data.userPreferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    fun saveInvoiceOr(orID: String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[INVOICE_OR] = orID ?: "none"
            }
        }

    }

    fun getInvoiceOr(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[INVOICE_OR] ?: "none"
            }.first()
        }
    }
/*
    fun getPassword(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[PASSWORD] ?: "none"
            }.first()
        }
    }

    fun savePassword(newPassword: String) {
        runBlocking {
            dataStore.edit { preference ->
                preference[PASSWORD] = newPassword
            }
        }


    }*/

    companion object {
        private val INVOICE_OR = stringPreferencesKey("invoice_or")
        private val PASSWORD = stringPreferencesKey("password")

    }
}