package com.beomsu317.privatechatapp.presentation.startup.sign_up

sealed class SignUpEvent {
    data class SignUp(
        val displayName: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : SignUpEvent()
}
