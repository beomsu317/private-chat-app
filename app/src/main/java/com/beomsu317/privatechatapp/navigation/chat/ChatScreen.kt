package com.beomsu317.privatechatapp.navigation.chat

sealed class ChatScreen(val route: String) {
    object ChatRoomListScreen: ChatScreen(route = "chat_room_list_screen")
    object ChatRoomScreen: ChatScreen(route = "chat_room_screen")
}