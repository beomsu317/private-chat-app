package com.beomsu317.chat_presentation.chat_room

sealed class ChatRoomEvent {
    data class SendMessage(val text: String) : ChatRoomEvent()
}
