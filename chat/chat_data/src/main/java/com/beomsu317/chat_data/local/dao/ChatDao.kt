package com.beomsu317.chat_data.local.dao

import androidx.room.*
import com.beomsu317.chat_data.local.entity.FriendEntity
import com.beomsu317.core.domain.model.Message
import com.beomsu317.chat_data.local.entity.MessageEntity
import com.beomsu317.chat_data.local.entity.RoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Insert
    suspend fun insertMessage(messageEntity: MessageEntity)

    @Query("SELECT * FROM messageentity WHERE roomId = :roomId ORDER BY timestamp")
    fun getMessages(roomId: String): Flow<List<Message>>

    @Query("SELECT * FROM messageentity GROUP BY roomId HAVING MAX(timestamp)")
    fun getLastMessageFlow(): Flow<List<Message>>

    @Query("SELECT * FROM friendentity WHERE id = :friendId")
    fun getFriend(friendId: String): FriendEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFriend(friend: FriendEntity)

    @Query("UPDATE messageentity SET read = 1 WHERE roomId = :roomId")
    suspend fun updateMessagesReadToTrue(roomId: String)

    @Insert
    suspend fun insertRoom(roomEntity: RoomEntity)

    @Query("SELECT * FROM roomentity WHERE id = :roomId")
    suspend fun getRoomInfo(roomId: String): RoomEntity

    @Query("DELETE FROM roomentity WHERE id = :roomId")
    suspend fun deleteRoom(roomId: String)

    @Query("DELETE FROM messageentity WHERE roomId = :roomId")
    suspend fun deleteRoomMessages(roomId: String)

    @Query("DELETE FROM messageentity")
    suspend fun removeMessages()
}