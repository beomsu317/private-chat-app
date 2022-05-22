package com.beomsu317.chat_data.repository

import com.beomsu317.chat_data.remote.PrivateChatApi
import com.beomsu317.chat_data.remote.request.CreateRoomRequest
import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.domain.model.Room
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class ChatRepositoryImpl(
    private val api: PrivateChatApi,
    private val appDataStore: AppDataStore,
    private val dispatcher: CoroutineDispatcher
): ChatRepository {

    override suspend fun createRoom(userId: String): Room {
        return withContext(dispatcher) {
            val token = appDataStore.tokenFlow.first()
            val response = api.createRoom(auth = "Bearer ${token}", request = CreateRoomRequest(userId))
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
}