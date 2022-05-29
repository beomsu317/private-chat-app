package com.beomsu317.chat_domain.use_case

import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.core.domain.model.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesFlowUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(roomId: String): Flow<List<Message>> =
        chatRepository.getMessagesFlow(roomId)

}