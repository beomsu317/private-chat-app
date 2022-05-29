package com.beomsu317.chat_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val senderId: String,
    val roomId: String,
    val timestamp: Long,
    val displayName: String,
    val message: String,
    val photoUrl: String,
    val read: Boolean
)