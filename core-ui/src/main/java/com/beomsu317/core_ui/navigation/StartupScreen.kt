package com.beomsu317.core_ui.navigation

sealed class StartupScreen(val route: String) {
    object SplashScreen : StartupScreen(route = "splash_screen")
    object IntroScreen : StartupScreen(route = "intro_screen")
    object SignInScreen : StartupScreen(route = "sign_in_screen")
    object SignUpScreen : StartupScreen(route = "sign_up_screen")
}
