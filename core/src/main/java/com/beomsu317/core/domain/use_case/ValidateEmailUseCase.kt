package com.beomsu317.core.domain.use_case

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    suspend operator fun invoke(email: String): Boolean {
        if (email.isNullOrBlank()) {
            return false
        }
        if (!Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$").containsMatchIn(email)) {
            return false
        }
        return true
    }
}