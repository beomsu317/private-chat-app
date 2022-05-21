package com.beomsu317.profile_data.repository

import android.content.Context
import android.net.Uri
import com.beomsu317.core.data.mapper.toUser
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.domain.model.User
import com.beomsu317.profile_data.remote.PrivateChatApi
import com.beomsu317.profile_domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.Exception

class ProfileRepositoryImpl(
    private val api: PrivateChatApi,
    private val context: Context,
    private val appDataStore: AppDataStore
): ProfileRepository {

    override suspend fun getProfile(token: String): User {
        val response = api.getProfile(auth = "Bearer ${token}")
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val result = response.body()?.result ?: throw Exception("Get profile error occured")
        val user = result.user.toUser()
        appDataStore.updateUser(user)
        return user
    }

    override suspend fun signOut() {
        appDataStore.updateToken(token = "")
        appDataStore.updateUser(user = User())
    }

    override suspend fun uploadProfileImage(uri: Uri): String {
        val inputStream =
            context.contentResolver.openInputStream(uri) ?: throw Exception("Inputstream is null")
        val requestBody =
            inputStream?.readBytes()?.toRequestBody(contentType = "image/*".toMediaType())
        val part = MultipartBody.Part.createFormData("profile", "profile", requestBody)
        inputStream.close()

        val token = appDataStore.tokenFlow.first()
        val response = api.uploadProfileImage(auth = "Bearer ${token}", image = part)
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val photoUrl =
            response.body()?.result?.photoUrl ?: throw Exception("Upload profile image error occured")
        appDataStore.updateUser(appDataStore.userFlow.first().copy(photoUrl = photoUrl))
        return photoUrl
    }
}