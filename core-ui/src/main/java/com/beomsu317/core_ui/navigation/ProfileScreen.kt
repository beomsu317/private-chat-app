package com.beomsu317.core_ui.navigation

sealed class ProfileScreen(val route: String) {
    object MyProfileScreen: ProfileScreen(route = "my_profile_screen")
    object SettingsScreen: ProfileScreen(route = "settings_screen")
}
