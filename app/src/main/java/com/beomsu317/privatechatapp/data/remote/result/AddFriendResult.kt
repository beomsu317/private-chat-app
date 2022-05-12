package com.beomsu317.privatechatapp.data.remote.result

import com.beomsu317.privatechatapp.data.remote.dto.FriendDto
import kotlinx.serialization.Serializable

@Serializable
data class AddFriendResult(
    val friends: Set<FriendDto>
)
