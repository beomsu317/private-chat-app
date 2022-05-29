package com.beomsu317.chat_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.beomsu317.chat_data.local.converter.Converter
import com.beomsu317.chat_data.local.dao.ChatDao
import com.beomsu317.chat_data.local.entity.FriendEntity
import com.beomsu317.chat_data.local.entity.MessageEntity
import com.beomsu317.chat_data.local.entity.RoomEntity

@Database(
    entities = [MessageEntity::class, FriendEntity::class, RoomEntity::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class ChatDatabase: RoomDatabase() {

    abstract fun chatDao(): ChatDao
}