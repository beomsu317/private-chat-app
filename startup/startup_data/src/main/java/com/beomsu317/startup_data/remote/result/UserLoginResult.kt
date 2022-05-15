package com.beomsu317.startup_data.remote.result

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResult(
    val token: String
)
