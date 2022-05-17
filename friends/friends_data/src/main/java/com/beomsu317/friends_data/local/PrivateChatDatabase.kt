package com.beomsu317.friends_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.beomsu317.friends_data.local.dao.FriendsDao
import com.beomsu317.friends_data.local.entity.FriendEntity

@Database(
    entities = [FriendEntity::class],
    version = 1
)
abstract class PrivateChatDatabase: RoomDatabase() {

    abstract fun friendsDao(): FriendsDao
}