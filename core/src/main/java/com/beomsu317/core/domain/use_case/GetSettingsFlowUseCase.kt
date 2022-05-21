package com.beomsu317.core.domain.use_case

import com.beomsu317.core.domain.model.Settings
import com.beomsu317.core.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsFlowUseCase @Inject constructor(
    private val repository: CoreRepository
) {
    operator fun invoke(): Flow<Settings> {
        return repository.getSettingsFlow()
    }
}