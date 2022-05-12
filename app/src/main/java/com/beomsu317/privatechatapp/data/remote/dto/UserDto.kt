package com.beomsu317.privatechatapp.data.remote.dto

import com.beomsu317.privatechatapp.domain.model.User
import com.beomsu317.privatechatapp.domain.model.UserFriend
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

fun UserDto.toEntity(): User {
    return User(
        id = id,
        email = email,
        displayName = displayName,
        photoUrl = photoUrl,
        friends = friends.map { it.toEntity() }.toSet(),
        rooms = rooms
    )
}

fun UserFriendDto.toEntity(): UserFriend {
    return UserFriend(
        id = id,
        priority = priority
    )
}