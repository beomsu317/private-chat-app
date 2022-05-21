package com.beomsu317.core_ui.navigation

sealed class ChatScreen(val route: String) {
    object ChatRoomListScreens: ChatScreen(route = "chat_room_list_screen")
}