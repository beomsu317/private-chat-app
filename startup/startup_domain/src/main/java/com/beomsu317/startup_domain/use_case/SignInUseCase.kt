package com.beomsu317.startup_domain.use_case

import com.beomsu317.core.common.Resource
import com.beomsu317.startup_domain.repository.StartupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: StartupRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            repository.loginUser(email = email, password = password)
            emit(Resource.Success<Unit>(data = Unit))
        } catch (e: Exception) {
            emit(Resource.Error<Unit>(e.localizedMessage))
        }
    }
}