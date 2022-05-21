package com.beomsu317.core.domain.model

import com.beomsu317.privatechatapp.data.remote.dto.UserDto
import com.beomsu317.privatechatapp.data.remote.dto.UserFriendDto
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String = "",
    val friends: Set<UserFriend> = emptySet(),
    val rooms: Set<String> = emptySet()
)

@Serializable
data class UserFriend(
    val id: String = "",
    val priority: Int = 0
)

fun UserFriend.toDto(): UserFriendDto {
    return UserFriendDto(
        id = id,
        priority = priority
    )
}

fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        email = email,
        displayName = displayName,
        photoUrl = photoUrl,
        friends = friends.map { it.toDto() }.toSet(),
        rooms = rooms
    )
}