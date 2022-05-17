package com.beomsu317.friends_domain.use_case

import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.model.UserFriend
import com.beomsu317.friends_domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class AddFriendUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(token: String, userFriend: UserFriend): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            repository.addFriend(token = token, userFriend = userFriend)
            emit(Resource.Success<Unit>(data = Unit))
        } catch (e: Exception) {
            emit(Resource.Error<Unit>(e.localizedMessage))
        }
    }
}