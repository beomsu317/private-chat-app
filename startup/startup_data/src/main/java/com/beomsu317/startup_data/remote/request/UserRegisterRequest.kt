package com.beomsu317.startup_data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterRequest(
    val displayName: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)
