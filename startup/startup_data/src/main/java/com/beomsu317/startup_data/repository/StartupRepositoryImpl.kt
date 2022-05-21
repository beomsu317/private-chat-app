package com.beomsu317.startup_data.repository

import com.beomsu317.core.data.mapper.toUser
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.startup_data.remote.PrivateChatApi
import com.beomsu317.startup_data.remote.request.UserSignInRequest
import com.beomsu317.startup_data.remote.request.UserRegisterRequest
import com.beomsu317.startup_domain.repository.StartupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class StartupRepositoryImpl(
    private val api: PrivateChatApi,
    private val appDataStore: AppDataStore,
    private val dispatcher: CoroutineDispatcher
) : StartupRepository {

    override suspend fun registerUser(
        displayName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        withContext(dispatcher) {
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
    }

    override suspend fun loginUser(email: String, password: String) {
        withContext(dispatcher) {
            val response = api.signInUser(
                UserSignInRequest(
                    email = email,
                    password = password
                )
            )
            if (!response.isSuccessful) {
                throw Exception(response.message())
            }
            val result = response.body()?.result ?: throw Exception("Sign in user error occured")
            val token = result.token
            val userDto = result.user
            appDataStore.updateToken(token)
            appDataStore.updateUser(userDto.toUser())
        }
    }
}