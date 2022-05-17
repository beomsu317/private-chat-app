package com.beomsu317.startup_data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class UserSignInRequest(
    val email: String,
    val password: String
)
