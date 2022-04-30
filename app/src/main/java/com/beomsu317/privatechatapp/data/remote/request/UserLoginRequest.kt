package com.beomsu317.privatechatapp.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginRequest(
    val email: String,
    val password: String
)
