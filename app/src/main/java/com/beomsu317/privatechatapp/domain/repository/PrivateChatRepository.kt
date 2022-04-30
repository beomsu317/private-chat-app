package com.beomsu317.privatechatapp.domain.repository

interface PrivateChatRepository {

    suspend fun registerUser(displayName: String, email: String, password: String, confirmPassword: String)

    suspend fun loginUser(email: String, password: String): String
}