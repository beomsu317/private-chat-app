package com.beomsu317.privatechatapp.domain.use_case

import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.model.Client
import com.beomsu317.privatechatapp.domain.model.User
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: PrivateChatRepository,
    private val client: Client
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val (token, user) = repository.loginUser(email = email, password = password)
            client.token = token
            client.user = user
            emit(Resource.Success<User>(data = user))
        } catch (e: Exception) {
            emit(Resource.Error<User>(e.localizedMessage))
        }
    }
}