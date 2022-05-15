package com.beomsu317.startup_data.repository

import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.startup_data.remote.PrivateChatApi
import com.beomsu317.startup_data.remote.request.UserLoginRequest
import com.beomsu317.startup_data.remote.request.UserRegisterRequest
import com.beomsu317.startup_domain.repository.StartupRepository
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class StartupRepositoryImpl(
    private val api: PrivateChatApi,
    private val appDataStore: AppDataStore
) : StartupRepository {

    override suspend fun registerUser(
        displayName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val response = api.registerUser(
            UserRegisterRequest(
                displayName = displayName,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        )
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
    }

    override suspend fun loginUser(email: String, password: String) {
        val response = api.loginUser(
            UserLoginRequest(
                email = email,
                password = password
            )
        )
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val token = response.body()?.result?.token ?: throw Exception("Login user error occured")
        appDataStore.updateToken(token)
    }

}