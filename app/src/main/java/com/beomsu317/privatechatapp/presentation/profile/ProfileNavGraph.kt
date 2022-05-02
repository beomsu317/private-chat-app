package com.beomsu317.privatechatapp.presentation.profile

import androidx.compose.material.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.beomsu317.privatechatapp.presentation.PROFILE_GRAPH_ROUTE
import com.beomsu317.privatechatapp.presentation.startup.StartupScreen

sealed class ProfileScreen(val route: String) {
    object MyProfileScreen: ProfileScreen(route = "my_profile_screen")
}

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
//                    navController.findDestination(FRIENDS_GRAPH_ROUTE)?.let {
//                        navController.popBackStack(it.id, true)
//                    }
//                    navController.findDestination(CHAT_GRAPH_ROUTE)?.let {
//                        navController.popBackStack(it.id, true)
//                    }
                    navController.navigate(StartupScreen.SignInScreen.route) {
                        popUpTo(ProfileScreen.MyProfileScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}