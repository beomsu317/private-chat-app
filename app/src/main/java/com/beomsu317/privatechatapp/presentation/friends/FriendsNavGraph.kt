package com.beomsu317.privatechatapp.presentation.friends

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.beomsu317.privatechatapp.presentation.FRIENDS_GRAPH_ROUTE
import com.beomsu317.privatechatapp.presentation.friends.friend_profile.FriendProfileScreen

sealed class FriendsScreen(val route: String) {
    object FriendsListScreen: FriendsScreen(route = "friends_list_screen")
    object FriendProfileScreen: FriendsScreen(route = "friend_profile_screen")
}

fun NavGraphBuilder.friendsNavGraph(navHostController: NavHostController) {
    navigation(
        startDestination = FriendsScreen.FriendsListScreen.route,
        route = FRIENDS_GRAPH_ROUTE
    ) {
        composable(FriendsScreen.FriendsListScreen.route) {
            FriendsListScreen(navHostController)
        }
        composable(FriendsScreen.FriendProfileScreen.route) {
            FriendProfileScreen()
        }
    }
}