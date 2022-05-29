package com.beomsu317.chat_domain.use_case

import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(message: Message): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            chatRepository.sendMessage(message)
            emit(Resource.Success<Unit>(data = Unit))
        } catch (e: IOException) {
            emit(Resource.Error<Unit>(e.localizedMessage))
        }
    }
}