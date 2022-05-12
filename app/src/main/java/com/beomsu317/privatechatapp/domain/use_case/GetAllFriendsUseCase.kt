package com.beomsu317.privatechatapp.domain.use_case

import android.util.Log
import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.model.Friend
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetAllFriendsUseCase @Inject constructor(
    private val repository: PrivateChatRepository
) {
    suspend operator fun invoke(): Flow<Resource<Set<Friend>>> = flow {
        try {
            emit(Resource.Loading<Set<Friend>>())
            val friends = repository.getAllFriends()
            val user = repository.getClient().user
            val userFriendsIds = user.friends.map { it.id }
            val result = friends.filter { friend ->
                !userFriendsIds.contains(friend.id)
            }.toSet()
            emit(Resource.Success<Set<Friend>>(data = result))
        } catch (e: Exception) {
            emit(Resource.Error<Set<Friend>>(message = e.localizedMessage))
        }
    }
}