package com.beomsu317.core.data.repository

import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.domain.repository.AppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dispatcher: CoroutineDispatcher
): AppRepository {
    override suspend fun getToken(): String? {
        return withContext(dispatcher) {
            appDataStore.tokenFlow.firstOrNull()
        }
    }
}