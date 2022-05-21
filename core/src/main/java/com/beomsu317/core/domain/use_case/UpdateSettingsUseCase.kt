package com.beomsu317.core.domain.use_case

import com.beomsu317.core.domain.model.Settings
import com.beomsu317.core.domain.repository.CoreRepository
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val repository: CoreRepository
) {
    suspend operator fun invoke(settings: Settings) {
        repository.updateSettings(settings)
    }
}