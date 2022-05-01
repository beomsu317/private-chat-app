package com.beomsu317.privatechatapp.data.remote.dto

import com.beomsu317.privatechatapp.domain.model.User

@kotlinx.serialization.Serializable
data class UserDto(
    val id: String,
    val email: String,
    val displayName: String,
    val photoUrl: String,
    val friends: Set<String>,
    val rooms: Set<String>
)

fun UserDto.toUser(): User {
    return User(
        id = id,
        email = email,
        displayName = displayName,
        photoUrl = photoUrl,
        friends = friends,
        rooms = rooms
    )
}