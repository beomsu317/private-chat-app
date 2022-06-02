package com.beomsu317.chat_domain.use_case

import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.core.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class LeaveRoomUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(roomId: String): Flow<Resource<Unit>> = flow {
        try {
            Resource.Loading<Unit>()
            repository.leaveRoom(roomId)
            Resource.Success<Unit>(data = Unit)
        } catch (e: IOException) {
            Resource.Error<Unit>(message = e.localizedMessage)
        }
    }
}