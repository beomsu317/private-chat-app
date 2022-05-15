package com.beomsu317.profile_domain.use_case

import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.model.User
import com.beomsu317.profile_domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(token: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val result = repository.getProfile(token)
            emit(Resource.Success<User>(data = result))
        } catch (e: Exception) {
            emit(Resource.Error<User>(e.localizedMessage))
        }
    }
}