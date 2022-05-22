package com.beomsu317.friends_presentation

import android.util.Log
import androidx.compose.material.SnackbarDuration
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core_ui.navigation.ChatScreen
import com.beomsu317.core_ui.navigation.FRIENDS_GRAPH_ROUTE
import com.beomsu317.core_ui.navigation.FriendsScreen
import com.beomsu317.friends_presentation.add_friends.AddFriendsScreen
import com.beomsu317.friends_presentation.friends_list.FriendsListScreen
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
                onOneOnOneChatClick = {
                    val encodedFriend = Json.encodeToString<Friend>(it)
                    navController.navigate(
                        "${ChatScreen.ChatRoomScreen.route}/${
                            URLEncoder.encode(
                                encodedFriend,
                                "UTF-8"
                            )
                        }",
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