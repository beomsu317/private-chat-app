package com.beomsu317.core.data.repository

import android.util.Log
import com.beomsu317.core.data.remote.PrivateChatApi
import com.beomsu317.core.data.remote.request.UpdateUserRequest
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.domain.model.Settings
import com.beomsu317.core.domain.model.User
import com.beomsu317.core.domain.model.toDto
import com.beomsu317.core.domain.repository.CoreRepository
import com.beomsu317.privatechatapp.data.remote.dto.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.lang.Exception
import javax.inject.Inject

class CoreRepositoryImpl @Inject constructor(
    private val api: PrivateChatApi,
    private val appDataStore: AppDataStore
): CoreRepository {

    override suspend fun updateUser(user: User) {
        val token = appDataStore.tokenFlow.first()
        val response = api.updateUser(auth = "Bearer ${token}", UpdateUserRequest(userDto = user.toDto()))
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        appDataStore.updateUser(user)
    }

    override suspend fun updateSettings(settings: Settings) {
        appDataStore.updateSettings(settings)
    }

    override suspend fun updateToken(token: String) {
        appDataStore.updateToken(token)
    }

    override fun getUserFlow(): Flow<User> {
        return appDataStore.userFlow
    }

    override fun getSettingsFlow(): Flow<Settings> {
        return appDataStore.settingsFlow
    }

    override fun getTokenFlow(): Flow<String> {
        return appDataStore.tokenFlow
    }

}