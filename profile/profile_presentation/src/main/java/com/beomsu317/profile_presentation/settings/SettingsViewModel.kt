package com.beomsu317.profile_presentation.settings

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.domain.model.Settings
import com.beomsu317.core.domain.repository.CoreRepository
import com.beomsu317.core.domain.use_case.CoreUseCases
import com.beomsu317.core.domain.use_case.UpdateSettingsUseCase
import com.beomsu317.profile_domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val coreUseCases: CoreUseCases,
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
            val settings = coreUseCases.getSettingsFlowUseCase().first()
            state = state.copy(settings = settings)
        }
    }

    private fun updateSettings(settings: Settings) {
        viewModelScope.launch {
            coreUseCases.updateSettingsUseCase(settings)
        }
    }
}