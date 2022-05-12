package com.beomsu317.privatechatapp.domain.model

import com.beomsu317.privatechatapp.data.local.room.entity.FriendEntity
import com.beomsu317.privatechatapp.data.remote.dto.FriendDto
import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val id: String,
    val email: String,
    val photoUrl: String,
    val displayName: String
)

fun Friend.toEntity(): FriendEntity {
    return FriendEntity(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName
    )
}