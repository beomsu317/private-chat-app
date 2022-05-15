package com.beomsu317.core.domain.use_case

import com.beomsu317.core.domain.repository.AppRepository
import javax.inject.Inject


class GetTokenUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): String? {
        return repository.getToken()
    }
}