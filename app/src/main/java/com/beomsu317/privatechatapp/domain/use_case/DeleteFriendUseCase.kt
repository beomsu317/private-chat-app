package com.beomsu317.privatechatapp.domain.use_case

import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.model.Friend
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class DeleteFriendUseCase(
    private val repository: PrivateChatRepository
) {

    suspend operator fun invoke(friend: Friend): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            repository.deleteFriend(friend)
            emit(Resource.Success<Unit>(data = Unit))
        } catch (e: Exception) {
            emit(Resource.Error<Unit>(e.localizedMessage))
        }
    }
}