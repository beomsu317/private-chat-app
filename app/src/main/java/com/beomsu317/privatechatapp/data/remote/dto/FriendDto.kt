package com.beomsu317.privatechatapp.data.remote.dto

import com.beomsu317.privatechatapp.data.local.room.entity.FriendEntity
import com.beomsu317.privatechatapp.domain.model.Friend
import kotlinx.serialization.Serializable

@Serializable
data class FriendDto(
    val id: String,
    val email: String,
    val photoUrl: String,
    val displayName: String
)

fun FriendDto.toEntity(): FriendEntity {
    return FriendEntity(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName
    )
}

fun FriendDto.toFriend(): Friend {
    return Friend(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName
    )
}