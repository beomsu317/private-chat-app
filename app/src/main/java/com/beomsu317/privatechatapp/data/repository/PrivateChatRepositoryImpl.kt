package com.beomsu317.privatechatapp.data.repository

import com.beomsu317.privatechatapp.data.remote.PrivateChatApi
import com.beomsu317.privatechatapp.data.remote.request.UserLoginRequest
import com.beomsu317.privatechatapp.data.remote.request.UserRegisterRequest
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import java.lang.Exception
import javax.inject.Inject

class PrivateChatRepositoryImpl @Inject constructor(
    private val api: PrivateChatApi
) : PrivateChatRepository {
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

    override suspend fun loginUser(email: String, password: String): String {
        val response = api.loginUser(
            UserLoginRequest(
                email = email,
                password = password
            )
        )
        if (!response.isSuccessful) {
            throw Exception(response.message())
        } else {
            val body = response.body() ?: throw Exception("response body is null")
            val result = body.result ?: throw Exception("result is null")
            return result.token
        }
    }

}