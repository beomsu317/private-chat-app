package com.beomsu317.profile_presentation.settings

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.domain.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appDataStore: AppDataStore
): ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    init {
        getSettings()
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.NotificationsToggle -> {
                state = state.copy(settings = state.settings.copy(notifications = event.value))
                updateSettings(state.settings)
            }
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            val settings = appDataStore.getSettings()
            state = state.copy(settings = settings)
        }
    }

    private fun updateSettings(settings: Settings) {
        viewModelScope.launch {
            appDataStore.updateSettings(settings)
        }
    }
}