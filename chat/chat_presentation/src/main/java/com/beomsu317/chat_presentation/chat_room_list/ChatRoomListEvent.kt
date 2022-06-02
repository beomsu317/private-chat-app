package com.beomsu317.chat_presentation.chat_room_list

sealed class ChatRoomListEvent {
    data class LeaveRoom(val roomId: String): ChatRoomListEvent()
}
