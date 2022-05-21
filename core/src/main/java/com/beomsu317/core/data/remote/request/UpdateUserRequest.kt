package com.beomsu317.core.data.remote.request

import com.beomsu317.privatechatapp.data.remote.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val userDto: UserDto
)
