package com.beomsu317.friends_data.remote.result

import com.beomsu317.friends_data.remote.dto.FriendDto
import kotlinx.serialization.Serializable

@Serializable
data class GetUserFriendsResult(
    val friends: Set<FriendDto>
)
