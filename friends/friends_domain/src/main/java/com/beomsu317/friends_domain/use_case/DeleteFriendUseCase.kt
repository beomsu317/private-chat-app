package com.beomsu317.friends_domain.use_case

import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.model.Friend
import com.beomsu317.friends_domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class DeleteFriendUseCase(
    private val repository: FriendsRepository
) {

    suspend operator fun invoke(friendId: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            repository.deleteFriendById(friendId = friendId)
            emit(Resource.Success<Unit>(data = Unit))
        } catch (e: Exception) {
            emit(Resource.Error<Unit>(e.localizedMessage))
        }
    }
}