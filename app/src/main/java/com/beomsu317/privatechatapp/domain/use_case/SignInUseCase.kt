package com.beomsu317.privatechatapp.domain.use_case

import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: PrivateChatRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            val token = repository.loginUser(email = email, password = password)
            emit(Resource.Success<String>(token))
        } catch (e: Exception) {
            emit(Resource.Error<String>(e.localizedMessage))
        }
    }
}