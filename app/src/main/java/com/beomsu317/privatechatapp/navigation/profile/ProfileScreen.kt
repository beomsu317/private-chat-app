package com.beomsu317.privatechatapp.navigation.profile

sealed class ProfileScreen(val route: String) {
    object MyProfileScreen: ProfileScreen(route = "my_profile_screen")
    object SettingsScreen: ProfileScreen(route = "settings_screen")
}
