package com.beomsu317.chat_domain.use_case

import com.beomsu317.chat_domain.repository.ChatRepository
import javax.inject.Inject

class RemoveMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke() {
        chatRepository.removeMessages()
    }
}