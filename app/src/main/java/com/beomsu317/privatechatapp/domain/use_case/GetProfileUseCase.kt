package com.beomsu317.privatechatapp.domain.use_case

import com.beomsu317.core.common.Resource
import com.beomsu317.privatechatapp.domain.model.User
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: PrivateChatRepository
) {
    suspend operator fun invoke(): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val result = repository.getProfile()
            emit(Resource.Success<User>(data = result))
        } catch (e: Exception) {
            emit(Resource.Error<User>(e.localizedMessage))
        }
    }
}