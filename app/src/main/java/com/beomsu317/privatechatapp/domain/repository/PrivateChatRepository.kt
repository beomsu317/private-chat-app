package com.beomsu317.privatechatapp.domain.repository

import com.beomsu317.privatechatapp.domain.model.User

interface PrivateChatRepository {

    suspend fun registerUser(displayName: String, email: String, password: String, confirmPassword: String)

    suspend fun loginUser(email: String, password: String): Pair<String, User>

    suspend fun getProfile(): User
}