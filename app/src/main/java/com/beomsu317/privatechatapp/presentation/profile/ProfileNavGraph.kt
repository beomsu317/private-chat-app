package com.beomsu317.privatechatapp.presentation.profile

import androidx.compose.material.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.beomsu317.privatechatapp.presentation.PROFILE_GRAPH_ROUTE

sealed class ProfileScreen(val route: String) {
    object MainProfileScreen: ProfileScreen(route = "main_profile_screen")
}

fun NavGraphBuilder.profileNavGraph(
    navHostController: NavHostController,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    navigation(
        startDestination = ProfileScreen.MainProfileScreen.route,
        route = PROFILE_GRAPH_ROUTE
    ) {
        composable(ProfileScreen.MainProfileScreen.route) {
            MainProfileScreen()
        }
    }
}