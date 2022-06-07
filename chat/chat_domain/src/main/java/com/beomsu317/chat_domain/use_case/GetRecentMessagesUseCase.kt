package com.beomsu317.chat_domain.use_case

import com.beomsu317.chat_domain.model.RecentMessage
import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.core.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecentMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    private val coreRepository: CoreRepository
) {
    operator fun invoke(): Flow<List<RecentMessage>> {
        return chatRepository.getLastMessagesFlow().map { messages ->
            messages.map { message ->
                val roomInfo = chatRepository.getRoomInfo(message.roomId)
                val user = coreRepository.getUserFlow().first()
                val friendId = (roomInfo.users - user.id).first()
                val friend = chatRepository.getFriend(friendId)
                RecentMessage(message, friend)
            }
        }
    }
}