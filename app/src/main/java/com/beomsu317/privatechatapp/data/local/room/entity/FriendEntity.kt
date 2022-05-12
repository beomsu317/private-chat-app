package com.beomsu317.privatechatapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beomsu317.privatechatapp.domain.model.Friend

@Entity
data class FriendEntity(
    @PrimaryKey val id: String,
    val email: String,
    val photoUrl: String,
    val displayName: String
)

fun FriendEntity.toFriend(): Friend {
    return Friend(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName
    )
}