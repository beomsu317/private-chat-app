package com.beomsu317.chat_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomEntity(
    @PrimaryKey val id: String,
    val owner: String,
    val users: Set<String>,
)