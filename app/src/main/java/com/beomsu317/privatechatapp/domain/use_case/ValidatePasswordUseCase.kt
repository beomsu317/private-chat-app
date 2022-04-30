package com.beomsu317.privatechatapp.domain.use_case

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean {
        if (password.isNullOrEmpty()) {
            return false
        }
        if (!Regex("^.{8,32}$").containsMatchIn(password)) {
            return false
        }
        return true
    }
}