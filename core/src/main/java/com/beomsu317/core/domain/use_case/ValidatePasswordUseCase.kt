package com.beomsu317.core.domain.use_case

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    suspend operator fun invoke(password: String): Boolean {
        if (password.isNullOrEmpty()) {
            return false
        }
        if (!Regex("^.{8,32}$").containsMatchIn(password)) {
            return false
        }
        return true
    }
}