package com.beomsu317.profile_data.repository

import android.content.Context
import android.net.Uri
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.domain.model.User
import com.beomsu317.privatechatapp.data.remote.dto.toUser
import com.beomsu317.profile_data.remote.PrivateChatApi
import com.beomsu317.profile_domain.repository.ProfileRepository
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
        val userDto = result.user
        return userDto.toUser()
    }

    override suspend fun signOut() {
        appDataStore.updateToken(token = "")
    }

    override suspend fun uploadProfileImage(token: String, uri: Uri): String {
        val inputStream =
            context.contentResolver.openInputStream(uri) ?: throw Exception("Inputstream is null")
        val requestBody =
            inputStream?.readBytes()?.toRequestBody(contentType = "image/*".toMediaType())
        val part = MultipartBody.Part.createFormData("profile", "profile", requestBody)
        inputStream.close()

        val response = api.uploadProfileImage(auth = "Bearer ${token}", image = part)
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val result =
            response.body()?.result ?: throw Exception("Upload profile image error occured")
        return result.photoUrl
    }
}