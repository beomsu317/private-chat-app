package com.beomsu317.core.data.local.data_store

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.beomsu317.core.domain.data_store.AppDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDataStoreImpl @Inject constructor(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher
) : AppDataStore {

    private val TOKEN_KEY = stringPreferencesKey("token")
    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

    override var token: String = ""

    override val tokenFlow: Flow<String> = context.preferencesDataStore.data.map { preferences ->
        preferences[TOKEN_KEY] ?: ""
    }.onEach {
        Log.d("AppDataStoreImpl", "token :${it}")
        token = it
    }

    override suspend fun updateToken(token: String) {
        withContext(dispatcher) {
            context.preferencesDataStore.edit { preferences ->
                preferences[TOKEN_KEY] = token
            }
        }
    }

    override suspend fun getToken(): String? {
        return withContext(dispatcher) {
            tokenFlow.firstOrNull()
        }
    }
}