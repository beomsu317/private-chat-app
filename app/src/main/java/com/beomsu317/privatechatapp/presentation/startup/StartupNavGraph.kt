package com.beomsu317.privatechatapp.presentation.startup

import androidx.compose.material.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.beomsu317.privatechatapp.presentation.STARTUP_GRAPH_ROUTE
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
    navHostController: NavHostController,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    navigation(
        startDestination = StartupScreen.SplashScreen.route,
        route = STARTUP_GRAPH_ROUTE,
    ) {
        composable(StartupScreen.SplashScreen.route) {
            SplashScreen(
                onNavigate = {
                    navHostController.popBackStack()
                    navHostController.navigate(
                        StartupScreen.IntroScreen.route
                    )
                }
            )
        }
        composable(StartupScreen.IntroScreen.route) {
            IntroScreen(onStartButtonClick = {
                navHostController.navigate(StartupScreen.SignInScreen.route)
            })
        }
        composable(StartupScreen.SignInScreen.route) {
            SignInScreen(
                onSignInButtonClick = {
//                    navHostController.navigate()
                },
                onSignUpButtonClick = {
                    navHostController.navigate(StartupScreen.SignUpScreen.route)
                }
            )
        }
        composable(StartupScreen.SignUpScreen.route) {
            SignUpScreen(
                showSnackbar = showSnackbar,
                onSignedUp = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}