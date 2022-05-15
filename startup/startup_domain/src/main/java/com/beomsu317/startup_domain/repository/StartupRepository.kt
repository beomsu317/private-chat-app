package com.beomsu317.startup_domain.repository

import kotlinx.coroutines.flow.Flow

interface StartupRepository {

    suspend fun registerUser(displayName: String, email: String, password: String, confirmPassword: String)

    suspend fun loginUser(email: String, password: String)
}