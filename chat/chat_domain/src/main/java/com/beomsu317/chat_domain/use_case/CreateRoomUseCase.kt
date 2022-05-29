package com.beomsu317.chat_domain.use_case

import android.util.Log
import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.model.Room
import com.beomsu317.core.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class CreateRoomUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke(userId: String): Flow<Resource<Room>> = flow {
        try {
            emit(Resource.Loading<Room>())
            val room = chatRepository.createRoom(userId)
            emit(Resource.Success<Room>(data = room))
        } catch (e: Exception) {
            emit(Resource.Error<Room>(e.localizedMessage))
        }
    }
}