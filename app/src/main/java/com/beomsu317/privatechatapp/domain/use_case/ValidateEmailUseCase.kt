package com.beomsu317.privatechatapp.domain.use_case

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String): Boolean {
        if (email.isNullOrEmpty()) {
            return false
        }
        if (!Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$").containsMatchIn(email)) {
            return false
        }
        return true
    }
}