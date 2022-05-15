package com.beomsu317.friends_presentation

import androidx.compose.material.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.beomsu317.core_ui.navigation.FRIENDS_GRAPH_ROUTE
import com.beomsu317.core_ui.navigation.FriendsScreen
import com.beomsu317.friends_presentation.add_friends.AddFriendsScreen
import com.beomsu317.friends_presentation.friend_profile.FriendProfileScreen
import com.beomsu317.friends_presentation.friends_list.FriendsListScreen

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