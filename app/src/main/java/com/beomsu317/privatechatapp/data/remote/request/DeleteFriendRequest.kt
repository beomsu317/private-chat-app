package com.beomsu317.privatechatapp.data.remote.request

import com.beomsu317.privatechatapp.data.remote.dto.UserFriendDto
import kotlinx.serialization.Serializable

@Serializable
data class DeleteFriendRequest(
    val friends: Set<UserFriendDto>
)
