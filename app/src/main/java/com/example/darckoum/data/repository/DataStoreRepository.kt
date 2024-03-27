package com.example.darckoum.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreRepository {

    object TokenManager {
        private val Context.dataStore by preferencesDataStore(name = "profile")
        private val TOKEN = stringPreferencesKey("token")

        suspend fun saveToken(context: Context, token: String) {
            context.dataStore.edit {
                it[TOKEN] = token
            }
        }

        suspend fun getToken(context: Context): String {
            val preferencesFlow = context.dataStore.data.map { preferences ->
                preferences[TOKEN] ?: ""
            }
            return preferencesFlow.first()
        }
    }
}
