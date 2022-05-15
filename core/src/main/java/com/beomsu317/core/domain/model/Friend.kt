package com.beomsu317.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val id: String,
    val email: String,
    val photoUrl: String,
    val displayName: String
)

//fun Friend.toEntity(): FriendEntity {
//    return FriendEntity(
//        id = id,
//        email = email,
//        photoUrl = photoUrl,
//        displayName = displayName
//    )
//}