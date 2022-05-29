package com.beomsu317.chat_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FriendEntity(
    @PrimaryKey val id: String,
    val email: String,
    val photoUrl: String,
    val displayName: String
)