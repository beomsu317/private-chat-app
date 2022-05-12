package com.beomsu317.privatechatapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.beomsu317.privatechatapp.data.local.room.dao.PrivateChatDao
import com.beomsu317.privatechatapp.data.local.room.entity.FriendEntity

@Database(
    entities = [FriendEntity::class],
    version = 1
)
abstract class PrivateChatDatabase: RoomDatabase() {

    abstract val dao: PrivateChatDao
}