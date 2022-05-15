package com.beomsu317.core_ui.common

sealed class OneTimeEvent {
    data class ShowSnackbar(val message: String): OneTimeEvent()
    object SignedUp: OneTimeEvent()
    object SignedIn: OneTimeEvent()
    object NeedSignIn: OneTimeEvent()
}