package com.beomsu317.privatechatapp.domain.use_case

import android.util.Log
import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.data.remote.response.PrivateChatResponse
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
            val user = repository.getProfile()
            emit(Resource.Success<User>(user))
        } catch (e: Exception) {
            emit(Resource.Error<User>(e.localizedMessage))
        }
    }
}