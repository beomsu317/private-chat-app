package com.beomsu317.chat_presentation

import androidx.compose.material.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.beomsu317.core_ui.navigation.CHAT_GRAPH_ROUTE

sealed class ChatScreen(val route: String) {
    object ChatRoomListScreens: ChatScreen(route = "chat_room_list_screen")
}

fun NavGraphBuilder.chatNavGraph(
    navHostController: NavHostController,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    navigation(
        startDestination = ChatScreen.ChatRoomListScreens.route,
        route = CHAT_GRAPH_ROUTE
    ) {
        composable(ChatScreen.ChatRoomListScreens.route) {
//            ChatRoomListScreen()
        }
    }
}
