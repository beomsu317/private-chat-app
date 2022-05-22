package com.beomsu317.chat_presentation

import androidx.compose.material.SnackbarDuration
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.beomsu317.chat_presentation.chat_room.ChatRoomScreen
import com.beomsu317.chat_presentation.chat_room_list.ChatRoomListScreen
import com.beomsu317.core_ui.navigation.CHAT_GRAPH_ROUTE
import com.beomsu317.core_ui.navigation.ChatScreen

fun NavGraphBuilder.chatNavGraph(
    navController: NavHostController,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    navigation(
        startDestination = ChatScreen.ChatRoomListScreen.route,
        route = CHAT_GRAPH_ROUTE
    ) {
        composable(ChatScreen.ChatRoomListScreen.route) {
            ChatRoomListScreen()
        }
        composable(
            route = "${ChatScreen.ChatRoomScreen.route}/{friend}",
            arguments = listOf(
                navArgument(
                    name = "friend",
                    builder = {
                        type = NavType.StringType
                    }
                )
            )
        ) {
            ChatRoomScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                showSnackbar = showSnackbar
            )
        }
    }
}
