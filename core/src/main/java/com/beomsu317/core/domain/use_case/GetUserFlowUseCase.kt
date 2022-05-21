package com.beomsu317.core.domain.use_case

import com.beomsu317.core.domain.model.User
import com.beomsu317.core.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserFlowUseCase @Inject constructor(
    private val repository: CoreRepository
) {
    operator fun invoke(): Flow<User> {
        return repository.getUserFlow()
    }
}