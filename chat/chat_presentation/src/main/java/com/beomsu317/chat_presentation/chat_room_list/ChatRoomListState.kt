package com.beomsu317.chat_presentation.chat_room_list

import com.beomsu317.chat_domain.model.RecentMessage
import com.beomsu317.core.domain.model.Message
import com.beomsu317.core.domain.model.Room

data class ChatRoomListState(
    val isLoading: Boolean = false,
    val recentMessage: List<RecentMessage> = emptyList(),
    val roomsInfo: List<Room> = emptyList()
)
