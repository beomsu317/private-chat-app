package com.beomsu317.profile_data.remote.result

import com.beomsu317.privatechatapp.data.remote.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class GetUserResult(
    val user: UserDto
)
