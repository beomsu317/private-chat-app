package com.beomsu317.chat_domain.repository

import com.beomsu317.core.domain.model.Room

interface ChatRepository {

    suspend fun createRoom(userId: String): Room
}