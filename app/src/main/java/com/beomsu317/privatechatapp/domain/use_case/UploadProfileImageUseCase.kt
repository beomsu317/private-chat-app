package com.beomsu317.privatechatapp.domain.use_case

import android.graphics.Bitmap
import android.net.Uri
import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(
    private val repository: PrivateChatRepository
) {
    suspend operator fun invoke(uri: Uri): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            repository.uploadProfileImage(uri)
            emit(Resource.Success<Unit>(data = Unit))
        } catch (e: Exception) {
            emit(Resource.Error<Unit>(message = e.localizedMessage))
        }
    }
}