package com.beomsu317.profile_presentation.settings

sealed class SettingsEvent {
    data class NotificationsToggle(val value: Boolean): SettingsEvent()
}