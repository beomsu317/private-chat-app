package com.beomsu317.privatechatapp.presentation.startup_presentation.sign_in

sealed class SignInEvent {
    data class SignIn(val email: String, val password: String) : SignInEvent()
}
