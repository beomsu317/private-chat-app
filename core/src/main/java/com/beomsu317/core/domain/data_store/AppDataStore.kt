package com.beomsu317.core.domain.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.beomsu317.core.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface AppDataStore {

    val Context.tokenDataStore: DataStore<Preferences>
    val TOKEN_KEY: Preferences.Key<String>
    val tokenFlow: Flow<String>

    val Context.settingsDataStore: DataStore<Settings>
    val settingsFlow: Flow<Settings>

    suspend fun updateToken(token: String)
    suspend fun getToken(): String

    suspend fun updateSettings(settings: Settings)
    suspend fun getSettings(): Settings
}