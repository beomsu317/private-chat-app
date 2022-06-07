package com.beomsu317.chat_presentation

import androidx.compose.material.SnackbarDuration
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.beomsu317.chat_domain.use_case.ChatUseCases
import com.beomsu317.chat_presentation.chat_room.ChatRoomScreen
import com.beomsu317.chat_presentation.chat_room_list.ChatRoomListScreen
import com.beomsu317.privatechatapp.navigation.bottom_navigation.CHAT_GRAPH_ROUTE
import com.beomsu317.privatechatapp.navigation.chat.ChatScreen
import com.beomsu317.privatechatapp.navigation.friends.FriendsScreen


fun NavGraphBuilder.chatNavGraph(
    navController: NavHostController,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    navigation(
        startDestination = ChatScreen.ChatRoomListScreen.route,
        route = CHAT_GRAPH_ROUTE
    ) {
        composable(ChatScreen.ChatRoomListScreen.route) {
            ChatRoomListScreen(
                onMessageClick = { friendId ->
                    navController.navigate("${ChatScreen.ChatRoomScreen.route}/${friendId}")
                },
                showSnackbar = showSnackbar
            )
        }
        composable(
            route = "${ChatScreen.ChatRoomScreen.route}/{friendId}",
            arguments = listOf(
                navArgument(
                    name = "friendId",
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
                onNavigateAddFriend = {
                    navController.navigate(FriendsScreen.AddFriendScreen.route)
                },
                showSnackbar = showSnackbar
            )
        }
    }
}
