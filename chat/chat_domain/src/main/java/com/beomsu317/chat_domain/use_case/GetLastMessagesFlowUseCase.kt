package com.beomsu317.chat_domain.use_case

import com.beomsu317.core.domain.model.Message
import com.beomsu317.chat_domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLastMessagesFlowUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    operator fun invoke(): Flow<List<Message>> {
        return chatRepository.getLastMessagesFlow()
    }
}