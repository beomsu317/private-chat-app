package com.beomsu317.core_ui.navigation

sealed class ChatScreen(val route: String) {
    object ChatRoomListScreen: ChatScreen(route = "chat_room_list_screen")
    object ChatRoomScreen: ChatScreen(route = "chat_room_screen")
}