package com.beomsu317.chat_presentation.chat_room

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core.domain.model.Message
import com.beomsu317.core.domain.model.Room
import com.beomsu317.core.domain.model.User

data class ChatRoomState(
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val room: Room = Room(),
    val friend: Friend = Friend(),
    val user: User = User()
)
