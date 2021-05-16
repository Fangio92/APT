package com.dizdarevic.apt

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.kodein.di.generic.contextFinder

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AppPreferences(val context: Context) {
    companion object {
        val USER_PASSWORD_KEY = stringPreferencesKey("PASSWORD")
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
    }

    suspend fun storeUser(username:String, password:String) {
        context.dataStore.edit {
            it[USER_PASSWORD_KEY] = password
            it[USER_NAME_KEY] = username
        }
    }

    val userNameFlow: Flow<String> = context.dataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }

    val passwordFlow: Flow<String> = context.dataStore.data.map {
        it[USER_PASSWORD_KEY] ?: ""
    }
}