package com.beomsu317.chat_domain.use_case

import android.util.Log
import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.model.Friend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetFriendUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(friendId: String): Flow<Resource<Friend>> = flow {
        try {
            emit(Resource.Loading<Friend>())
            val friend = chatRepository.getFriend(friendId)
            emit(Resource.Success<Friend>(data = friend))
        } catch (e: IOException) {
            emit(Resource.Error<Friend>(message = e.localizedMessage))
        }
    }

}