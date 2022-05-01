package com.beomsu317.privatechatapp.domain.use_case

import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository

class IsSignedInUseCase(
    private val repository: PrivateChatRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.isSigned()
    }
}