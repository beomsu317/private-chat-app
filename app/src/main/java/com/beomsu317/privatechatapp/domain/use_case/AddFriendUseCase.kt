package com.beomsu317.privatechatapp.domain.use_case

import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.model.UserFriend
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class AddFriendUseCase @Inject constructor(
    private val repository: PrivateChatRepository
) {
    suspend operator fun invoke(userFriend: UserFriend): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            repository.addFriend(userFriend = userFriend)
            emit(Resource.Success<Unit>(data = Unit))
        } catch (e: Exception) {
            emit(Resource.Error<Unit>(e.localizedMessage))
        }
    }
}