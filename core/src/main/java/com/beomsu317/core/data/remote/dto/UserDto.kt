package com.beomsu317.privatechatapp.data.remote.dto

import com.beomsu317.core.domain.model.User
import com.beomsu317.core.domain.model.UserFriend
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class UserDto(
    val id: String,
    val email: String,
    val displayName: String,
    val photoUrl: String,
    val friends: Set<UserFriendDto>,
    val rooms: Set<String>
)

@Serializable
data class UserFriendDto(
    val id: String,
    val priority: Int
)
