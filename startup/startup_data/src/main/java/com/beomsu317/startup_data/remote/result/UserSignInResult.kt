package com.beomsu317.startup_data.remote.result

import com.beomsu317.privatechatapp.data.remote.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class UserSignInResult(
    val token: String,
    val user: UserDto
)
