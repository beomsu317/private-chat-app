package com.beomsu317.chat_presentation.chat_room

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core.domain.model.Room

data class ChatRoomState(
    val isLoading: Boolean = false,
    val friend: Friend = Friend(),
    val room: Room = Room()
)
