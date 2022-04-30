package com.beomsu317.privatechatapp.data.repository

import com.beomsu317.privatechatapp.data.remote.PrivateChatApi
import com.beomsu317.privatechatapp.data.remote.request.UserLoginRequest
import com.beomsu317.privatechatapp.data.remote.request.UserRegisterRequest
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import java.net.HttpURLConnection

class PrivateChatRepositoryImpl(private val api: PrivateChatApi) : PrivateChatRepository {
    override suspend fun registerUser(email: String, password: String, confirmPassword: String) {
        val response = api.registerUser(
            UserRegisterRequest(
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        )
        if (!response.isSuccessful) {
            val body = response.body() ?: throw Exception("response body is null")
            throw Exception("${body.code} : ${body.message}")
        }
    }

    override suspend fun loginUser(email: String, password: String): String {
        val response = api.loginUser(UserLoginRequest(email = email, password = password)) ?: throw Exception("login error occured")
        val body = response.body() ?: throw Exception("response body is null")
        if (response.isSuccessful) {
            val result = body.result ?: throw Exception("result is null")
            return result.token
        } else {
            throw Exception("${body.code} : ${body.message}")
        }
    }
}