package com.beomsu317.core_ui.navigation

import android.content.Intent
import androidx.compose.material.SnackbarDuration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.beomsu317.core.common.startService
import com.beomsu317.privatechatapp.navigation.bottom_navigation.FRIENDS_GRAPH_ROUTE
import com.beomsu317.privatechatapp.navigation.bottom_navigation.STARTUP_GRAPH_ROUTE
import com.beomsu317.privatechatapp.navigation.startup.StartupScreen
import com.beomsu317.privatechatapp.presentation.startup_presentation.intro.IntroScreen
import com.beomsu317.privatechatapp.presentation.startup_presentation.sign_in.SignInScreen
import com.beomsu317.privatechatapp.presentation.startup_presentation.sign_up.SignUpScreen
import com.beomsu317.privatechatapp.presentation.startup_presentation.splash.SplashScreen
import com.beomsu317.privatechatapp.service.ChatService

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
                    navController.navigate(FRIENDS_GRAPH_ROUTE) {
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
            val context = LocalContext.current
            SignInScreen(
                onSignedIn = {
                    context.startService(ChatService::class.java)
                    navController.navigate(FRIENDS_GRAPH_ROUTE) {
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