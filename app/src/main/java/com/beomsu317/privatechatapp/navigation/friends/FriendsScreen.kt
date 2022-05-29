package com.beomsu317.privatechatapp.navigation.friends

sealed class FriendsScreen(val route: String) {
    object FriendsListScreen: FriendsScreen(route = "friends_list_screen")
    object AddFriendScreen: FriendsScreen(route = "add_friend_screen")
}