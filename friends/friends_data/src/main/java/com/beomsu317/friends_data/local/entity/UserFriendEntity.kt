package com.beomsu317.friends_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beomsu317.core.domain.model.Friend
import com.beomsu317.friends_data.remote.dto.FriendDto

@Entity
data class UserFriendEntity(
    @PrimaryKey val id: String,
    val email: String,
    val photoUrl: String,
    val displayName: String,
    val numberOfFriends: Int,
    val numberOfRooms: Int
)

