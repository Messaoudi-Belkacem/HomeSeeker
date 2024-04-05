package com.example.darckoum.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DataStoreRepository {

    object TokenManager {
        private val Context.dataStore by preferencesDataStore(name = "settings")
        private val TOKEN = stringPreferencesKey("token")

        suspend fun saveToken(context: Context, token: String) {
            context.dataStore.edit {settings ->
                settings[TOKEN] = token
            }
        }

        suspend fun getToken(context: Context): String? {
            val preferencesFlow = context.dataStore.data.first()
            return preferencesFlow[TOKEN]
        }

        suspend fun clearToken(context: Context) {
            withContext(Dispatchers.IO) {
                try {
                    context.dataStore.edit { settings ->
                        settings.remove(TOKEN)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
