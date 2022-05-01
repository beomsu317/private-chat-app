package com.beomsu317.privatechatapp.presentation.startup

import android.util.Log
import androidx.compose.material.SnackbarDuration
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.composable
import com.beomsu317.privatechatapp.presentation.BottomNavRoute
import com.beomsu317.privatechatapp.presentation.ROOT_GRAPH_ROUTE
import com.beomsu317.privatechatapp.presentation.STARTUP_GRAPH_ROUTE
import com.beomsu317.privatechatapp.presentation.bottomNavBarRoutes
import com.beomsu317.privatechatapp.presentation.friends.FriendsScreen
import com.beomsu317.privatechatapp.presentation.startup.intro.IntroScreen
import com.beomsu317.privatechatapp.presentation.startup.sign_in.SignInScreen
import com.beomsu317.privatechatapp.presentation.startup.splash.SplashScreen
import com.beomsu317.privatechatapp.presentation.ui.SignUpScreen

sealed class StartupScreen(val route: String) {
    object SplashScreen : StartupScreen(route = "splash_screen")
    object IntroScreen : StartupScreen(route = "intro_screen")
    object SignInScreen : StartupScreen(route = "sign_in_screen")
    object SignUpScreen : StartupScreen(route = "sign_up_screen")
}

fun NavGraphBuilder.startupNavGraph(
    navController: NavHostController,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    navigation(
        startDestination = StartupScreen.SplashScreen.route,
        route = STARTUP_GRAPH_ROUTE,
    ) {
        composable(StartupScreen.SplashScreen.route) {
            SplashScreen(
                onNavigateSignIn = {
                    navController.popBackStack()
                    navController.navigate(StartupScreen.IntroScreen.route)
                },
                onNavigateFriendsList = {
                    navController.popBackStack()
                    navController.navigate(BottomNavRoute.FriendsRoute.route) {
                        popUpTo(STARTUP_GRAPH_ROUTE) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(StartupScreen.IntroScreen.route) {
            IntroScreen(onStartButtonClick = {
                navController.navigate(StartupScreen.SignInScreen.route)
            })
        }
        composable(StartupScreen.SignInScreen.route) {
            SignInScreen(
                onSignedIn = {
                    navController.navigate(BottomNavRoute.FriendsRoute.route) {
                        popUpTo(STARTUP_GRAPH_ROUTE) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onSignUpButtonClick = {
                    navController.navigate(StartupScreen.SignUpScreen.route)
                },
                showSnackbar = showSnackbar
            )
        }
        composable(StartupScreen.SignUpScreen.route) {
            SignUpScreen(
                showSnackbar = showSnackbar,
                onSignedUp = {
                    navController.popBackStack()
                }
            )
        }
    }
}