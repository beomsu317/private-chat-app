package com.beomsu317.profile_presentation

import androidx.compose.material.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.beomsu317.core_ui.navigation.*
import com.beomsu317.profile_presentation.my_profile.MyProfileScreen
import com.beomsu317.profile_presentation.settings.SettingsScreen

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    navigation(
        startDestination = ProfileScreen.MyProfileScreen.route,
        route = PROFILE_GRAPH_ROUTE
    ) {
        composable(ProfileScreen.MyProfileScreen.route) {
            MyProfileScreen(
                showSnackbar = showSnackbar,
                onSignOut = {
                    navController.findDestination(FRIENDS_GRAPH_ROUTE)?.let {
                        navController.popBackStack(it.id, true)
                    }
                    navController.findDestination(CHAT_GRAPH_ROUTE)?.let {
                        navController.popBackStack(it.id, true)
                    }
                    navController.navigate(StartupScreen.SignInScreen.route) {
                        popUpTo(ProfileScreen.MyProfileScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateSettings = {
                    navController.navigate(ProfileScreen.SettingsScreen.route)
                },
                onClickFriends = {
                    navController.navigate(FriendsScreen.FriendsListScreen.route) {
                        popUpTo(PROFILE_GRAPH_ROUTE) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onClickRooms = {
                    navController.navigate(ChatScreen.ChatRoomListScreen.route) {
                        popUpTo(PROFILE_GRAPH_ROUTE) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(ProfileScreen.SettingsScreen.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}