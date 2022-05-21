package com.beomsu317.profile_domain.use_case

import android.net.Uri
import com.beomsu317.core.common.Resource
import com.beomsu317.profile_domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(uri: Uri): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            val result = repository.uploadProfileImage(uri = uri)
            emit(Resource.Success<String>(data = result))
        } catch (e: Exception) {
            emit(Resource.Error<String>(message = e.localizedMessage))
        }
    }
}