package com.beomsu317.core.domain.use_case

import com.beomsu317.core.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTokenFlowUseCase @Inject constructor(
    private val repository: CoreRepository
) {
    operator fun invoke(): Flow<String> {
        return repository.getTokenFlow()
    }
}