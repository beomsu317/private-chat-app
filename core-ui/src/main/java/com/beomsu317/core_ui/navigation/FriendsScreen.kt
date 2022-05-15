package com.beomsu317.core_ui.navigation

sealed class FriendsScreen(val route: String) {
    object FriendsListScreen: FriendsScreen(route = "friends_list_screen")
    object AddFriendScreen: FriendsScreen(route = "add_friend_screen")
    object FriendProfileScreen: FriendsScreen(route = "friend_profile_screen")
}