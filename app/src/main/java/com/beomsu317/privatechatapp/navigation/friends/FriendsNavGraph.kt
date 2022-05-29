package com.beomsu317.friends_presentation

import androidx.compose.material.SnackbarDuration
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.beomsu317.core.domain.model.Friend
import com.beomsu317.friends_presentation.add_friends.AddFriendsScreen
import com.beomsu317.friends_presentation.friends_list.FriendsListScreen
import com.beomsu317.privatechatapp.navigation.bottom_navigation.FRIENDS_GRAPH_ROUTE
import com.beomsu317.privatechatapp.navigation.chat.ChatScreen
import com.beomsu317.privatechatapp.navigation.friends.FriendsScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

fun NavGraphBuilder.friendsNavGraph(
    navController: NavHostController,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    navigation(
        startDestination = FriendsScreen.FriendsListScreen.route,
        route = FRIENDS_GRAPH_ROUTE
    ) {
        composable(FriendsScreen.FriendsListScreen.route) {
            FriendsListScreen(
                showSnackbar = showSnackbar,
                onAddFriendButtonClick = {
                    navController.navigate(FriendsScreen.AddFriendScreen.route)
                },
                onOneOnOneChatClick = { friendId ->
                    navController.navigate(
                        "${ChatScreen.ChatRoomScreen.route}/${friendId}",
                    ) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(FriendsScreen.AddFriendScreen.route) {
            AddFriendsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}