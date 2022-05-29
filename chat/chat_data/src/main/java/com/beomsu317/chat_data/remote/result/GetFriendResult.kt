package com.beomsu317.chat_data.remote.result

import com.beomsu317.core.data.remote.dto.FriendDto
import kotlinx.serialization.Serializable

@Serializable
data class GetFriendResult(
    val friend: FriendDto
)
