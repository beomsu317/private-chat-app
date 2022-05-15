package com.beomsu317.privatechatapp.domain.use_case

import com.beomsu317.core.common.Resource
import com.beomsu317.privatechatapp.domain.model.Friend
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class SearchFriendsUseCase(
    private val repository: PrivateChatRepository
) {
    suspend operator fun invoke(searchText: String): Flow<Resource<List<Friend>>> = flow {
        try {
            emit(Resource.Loading())
            val friends = repository.searchFriends(searchText = searchText)
            emit(Resource.Success<List<Friend>>(data = friends))
        } catch (e: Exception) {
            emit(Resource.Error<List<Friend>>(message = e.localizedMessage))
        }
    }
}