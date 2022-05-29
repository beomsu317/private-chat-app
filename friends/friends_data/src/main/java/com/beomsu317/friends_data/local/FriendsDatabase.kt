package com.beomsu317.friends_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.beomsu317.friends_data.local.dao.FriendsDao
import com.beomsu317.friends_data.local.entity.FriendEntity
import com.beomsu317.friends_data.local.entity.UserFriendEntity

@Database(
    entities = [UserFriendEntity::class, FriendEntity::class],
    version = 1
)
abstract class FriendsDatabase: RoomDatabase() {

    abstract fun friendsDao(): FriendsDao
}