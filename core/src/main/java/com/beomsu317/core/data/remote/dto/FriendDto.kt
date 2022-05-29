package com.beomsu317.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FriendDto(
    val id: String,
    val email: String,
    val photoUrl: String,
    val displayName: String,
    val numberOfFriends: Int,
    val numberOfRooms: Int
)
