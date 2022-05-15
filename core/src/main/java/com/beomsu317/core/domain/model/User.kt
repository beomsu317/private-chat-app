package com.beomsu317.core.domain.model

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
    val id: String,
    val priority: Int
)

//fun UserFriend.toDto(): UserFriendDto {
//    return UserFriendDto(
//        id = id,
//        priority = priority
//    )
//}