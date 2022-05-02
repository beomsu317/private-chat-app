package com.beomsu317.privatechatapp.domain.use_case

import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: PrivateChatRepository
) {

    suspend operator fun invoke() {
        repository.signOut()
    }
}