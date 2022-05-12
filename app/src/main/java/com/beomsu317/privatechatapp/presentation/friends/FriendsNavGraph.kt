package com.beomsu317.privatechatapp.presentation.friends

import androidx.compose.material.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.beomsu317.privatechatapp.presentation.FRIENDS_GRAPH_ROUTE
import com.beomsu317.privatechatapp.presentation.friends.add_friends.AddFriendsScreen
import com.beomsu317.privatechatapp.presentation.friends.friend_profile.FriendProfileScreen

sealed class FriendsScreen(val route: String) {
    object FriendsListScreen: FriendsScreen(route = "friends_list_screen")
    object AddFriendScreen: FriendsScreen(route = "add_friend_screen")
    object FriendProfileScreen: FriendsScreen(route = "friend_profile_screen")
}

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
        composable(FriendsScreen.FriendProfileScreen.route) {
            FriendProfileScreen()
        }
    }
}