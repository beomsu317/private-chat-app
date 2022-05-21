package com.beomsu317.core.domain.use_case

import com.beomsu317.core.domain.repository.CoreRepository
import javax.inject.Inject

class UpdateTokenUseCase @Inject constructor(
    private val repository: CoreRepository
) {
    suspend operator fun invoke(token: String) {
        repository.updateToken(token)
    }
}