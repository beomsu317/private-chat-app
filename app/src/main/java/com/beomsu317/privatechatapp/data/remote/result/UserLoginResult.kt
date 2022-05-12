package com.beomsu317.privatechatapp.data.remote.result

import com.beomsu317.privatechatapp.data.remote.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResult(
    val token: String
)
