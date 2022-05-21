package com.beomsu317.friends_domain.use_case

import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.model.Friend
import com.beomsu317.friends_domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetAllFriendsUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(): Flow<Resource<Set<Friend>>> = flow {
        try {
            emit(Resource.Loading<Set<Friend>>())
            val friends = repository.getAllFriends()
            emit(Resource.Success<Set<Friend>>(data = friends))
        } catch (e: Exception) {
            emit(Resource.Error<Set<Friend>>(message = e.localizedMessage))
        }
    }
}