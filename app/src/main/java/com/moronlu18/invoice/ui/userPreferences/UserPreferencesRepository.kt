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

    fun saveItemOrder(order: String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[ITEM_ORDER] = order
            }
        }
    }

    fun getItemOrder(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[ITEM_ORDER] ?: "default_value"
            }.first()
        }
    }
    fun saveTaskOrder(order: String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[TASK_ORDER] = order
            }
        }
    }

    fun getTaskOrder(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[TASK_ORDER] ?: "default"
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
        private val ITEM_ORDER = stringPreferencesKey("item_order")
        private val TASK_ORDER = stringPreferencesKey("task_order")
    }
}