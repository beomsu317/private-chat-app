package com.beomsu317.core.data.local.data_store

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.domain.model.Settings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDataStoreImpl @Inject constructor(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher
) : AppDataStore {

    override val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
    override val TOKEN_KEY = stringPreferencesKey("token")

    override val tokenFlow: Flow<String> = context.tokenDataStore.data.map { preferences ->
        preferences[TOKEN_KEY] ?: ""
    }

    override val Context.settingsDataStore: DataStore<Settings> by dataStore(
        fileName = "settings.pb",
        serializer = SettingsSerializer()
    )

    override val settingsFlow: Flow<Settings> = context.settingsDataStore.data

    override suspend fun updateToken(token: String) {
        withContext(dispatcher) {
            context.tokenDataStore.edit { preferences ->
                preferences[TOKEN_KEY] = token
            }
        }
    }

    override suspend fun getToken(): String {
        return withContext(dispatcher) {
            tokenFlow.first()
        }
    }

    override suspend fun updateSettings(settings: Settings) {
//        Log.d("TAG", "updateSettings: ${settings}")
        withContext(dispatcher) {
            context.settingsDataStore.updateData {
                settings
            }
        }
    }

    override suspend fun getSettings(): Settings {
        return withContext(dispatcher) {
            settingsFlow.first()
        }
    }
}