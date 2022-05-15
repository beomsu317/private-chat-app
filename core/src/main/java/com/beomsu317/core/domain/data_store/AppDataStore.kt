package com.beomsu317.core.domain.data_store

import kotlinx.coroutines.flow.Flow

interface AppDataStore {
    var token: String
    val tokenFlow: Flow<String>
    suspend fun updateToken(token: String)
    suspend fun getToken(): String?
}