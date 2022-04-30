package com.beomsu317.privatechatapp.presentation.common

sealed class OneTimeEvent {
    data class ShowSnackbar(val message: String): OneTimeEvent()
}