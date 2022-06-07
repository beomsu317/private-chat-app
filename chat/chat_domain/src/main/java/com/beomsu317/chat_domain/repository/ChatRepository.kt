package com.beomsu317.chat_domain.repository

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core.domain.model.Message
import com.beomsu317.core.domain.model.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun createRoom(userId: String): Room

    suspend fun getFriend(friendId: String): Friend

    suspend fun connectToChatServer(scope: CoroutineScope, onNotificate: (String, Message) -> Unit)

    suspend fun isConnected(): Boolean

    fun closeChatServer()

    suspend fun sendMessage(message: Message)

    suspend fun insertMessage(message: Message)

    fun getLastMessagesFlow(): Flow<List<Message>>

    fun getMessagesFlow(roomId: String): Flow<List<Message>>

    suspend fun updateMessagesReadToTrue(roomId: String)

    suspend fun getRoomInfo(roomId: String): Room

    suspend fun leaveRoom(roomId: String)

    suspend fun removeMessages()
}