package com.beomsu317.privatechatapp.domain.use_case

import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: PrivateChatRepository
) {
    operator fun invoke(
        displayName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Flow<Resource<Unit>> = flow<Resource<Unit>> {
        try {
            emit(Resource.Loading<Unit>())

            repository.registerUser(
                displayName = displayName,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
            emit(Resource.Success<Unit>(data = Unit))
        } catch (e: Exception) {
            emit(Resource.Error<Unit>(e.localizedMessage))
        }
    }
}