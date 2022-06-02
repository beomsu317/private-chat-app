package com.beomsu317.chat_data.repository

import com.beomsu317.chat_data.local.ChatDatabase
import com.beomsu317.chat_data.mapper.*
import com.beomsu317.chat_data.remote.PrivateChatApi
import com.beomsu317.chat_data.remote.dto.MessageDto
import com.beomsu317.chat_data.remote.request.CreateRoomRequest
import com.beomsu317.chat_data.remote.request.LeaveRoomRequest
import com.beomsu317.core.domain.model.Message
import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.core.common.Constants
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core.domain.model.Room
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*

class ChatRepositoryImpl(
    private val api: PrivateChatApi,
    private val database: ChatDatabase,
    private val appDataStore: AppDataStore,
    private val dispatcher: CoroutineDispatcher
) : ChatRepository {

    var chatServerWebSocket: WebSocket? = null

    override suspend fun createRoom(userId: String): Room {
        return withContext(dispatcher) {
            val token = appDataStore.tokenFlow.first()
            val response =
                api.createRoom(auth = "Bearer ${token}", request = CreateRoomRequest(userId))
            if (!response.isSuccessful) {
                throw Exception(response.message())
            }
            val room = response.body()?.result?.room ?: throw Exception("Response room is null")
            val user = appDataStore.userFlow.first()
            appDataStore.updateUser(
                user.copy(rooms = user.rooms + room.id)
            )
            room
        }
    }

    override suspend fun getFriend(friendId: String): Friend {
        return withContext(dispatcher) {
            val friendEntity = database.chatDao().getFriend(friendId) ?: run {
                val token = appDataStore.tokenFlow.first()
                val response = api.getFriend(auth = "Bearer ${token}", friendId = friendId)
                if (!response.isSuccessful) {
                    throw Exception(response.message())
                }
                val friendDto =
                    response.body()?.result?.friend ?: throw Exception("Response result is null")
                database.chatDao().insertFriend(friendDto.toEntity())
                database.chatDao().getFriend(friendId)
            }
            friendEntity.toFriend()
        }
    }

    override suspend fun connectToChatServer(
        scope: CoroutineScope,
        onNotificate: (String, Message) -> Unit
    ) {
        withContext(dispatcher) {
            chatServerWebSocket?.close(1000, "New connection")

            val token = appDataStore.tokenFlow.first()
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("${Constants.PRIVATE_CHAT_WEBSOCKET_URL}/chat/chat-server")
                .addHeader(name = "Authorization", value = "Bearer ${token}")
                .build()
            chatServerWebSocket = client.newWebSocket(request, object : WebSocketListener() {

                override fun onMessage(webSocket: WebSocket, text: String) {
                    super.onMessage(webSocket, text)
                    scope.launch {
                        try {
                            val message =
                                Json.decodeFromString<MessageDto>(text).toMessage(read = false)
                            onNotificate(message.displayName, message)

                            this@ChatRepositoryImpl.insertMessage(message = message)
                        } catch (e: SerializationException) {
                            e.printStackTrace()
                        }
                    }
                }
            })

            client.dispatcher.executorService.shutdown()
        }
    }

    override suspend fun isConnected(): Boolean {
        return chatServerWebSocket != null
    }

    override fun closeChatServer() {
        chatServerWebSocket?.close(1000, "Normal close")
        chatServerWebSocket = null
    }

    override suspend fun sendMessage(message: Message) {
        withContext(dispatcher) {
            chatServerWebSocket?.let {
                val messageDto = message.toDto()
                val encodedMessage = Json.encodeToString(messageDto)
                it.send(encodedMessage)
            }
        }
    }

    override suspend fun insertMessage(message: Message) {
        withContext(dispatcher) {
            database.chatDao().insertMessage(messageEntity = message.toEntity())
        }
    }

    override fun getLastMessagesFlow(): Flow<List<Message>> {
        return database.chatDao().getLastMessageFlow()
    }

    override fun getMessagesFlow(roomId: String): Flow<List<Message>> {
        return database.chatDao().getMessages(roomId = roomId)
    }

    override suspend fun updateMessagesReadToTrue(roomId: String) {
        database.chatDao().updateMessagesReadToTrue(roomId)
    }

    override suspend fun getRoomInfo(roomId: String): Room {
        return database.chatDao().getRoomInfo(roomId)?.toRoom() ?: run {
            val token = appDataStore.tokenFlow.first()
            val response =
                api.getRoomInfo(auth = "Bearer ${token}", roomId = roomId)
            if (!response.isSuccessful) {
                throw Exception(response.message())
            }
            val room = response.body()?.result?.room ?: throw Exception("Response body is null")
            database.chatDao().insertRoom(room.toEntity())
            database.chatDao().getRoomInfo(roomId).toRoom()
        }
    }

    override suspend fun leaveRoom(roomId: String) {
        val token = appDataStore.tokenFlow.first()
        val response = api.leaveRoom(auth = "Bearer ${token}", request = LeaveRoomRequest(roomId))
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        database.chatDao().deleteRoom(roomId)
        database.chatDao().deleteRoomMessages(roomId)

        val user = appDataStore.userFlow.first()
        appDataStore.updateUser(
            user = user.copy(
                rooms = user.rooms - roomId
            )
        )
    }
}