package com.beomsu317.privatechatapp.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import com.beomsu317.privatechatapp.data.local.data_store.ClientDataStore
import com.beomsu317.privatechatapp.data.remote.PrivateChatApi
import com.beomsu317.privatechatapp.data.remote.request.UserLoginRequest
import com.beomsu317.privatechatapp.data.remote.request.UserRegisterRequest
import com.beomsu317.privatechatapp.domain.model.Client
import com.beomsu317.privatechatapp.domain.model.User
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.InputStream
import java.lang.Exception
import java.net.URLEncoder
import javax.inject.Inject

class PrivateChatRepositoryImpl @Inject constructor(
    private val api: PrivateChatApi,
    private val clientDataStore: ClientDataStore,
    private val context: Context,
    private val client: Client
) : PrivateChatRepository {
    override suspend fun registerUser(
        displayName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val response = api.registerUser(
            UserRegisterRequest(
                displayName = displayName,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        )
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
    }

    override suspend fun loginUser(email: String, password: String) {
        val response = api.loginUser(
            UserLoginRequest(
                email = email,
                password = password
            )
        )
        if (!response.isSuccessful) {
            throw Exception(response.message())
        } else {
            val body = response.body() ?: throw Exception("response body is null")
            val result = body.result ?: throw Exception("result is null")
            val token = result.token
            val userDto = result.user
            clientDataStore.updateClient(
                Client(
                    token = token,
                    user = User(
                        id = userDto.id,
                        email = userDto.email,
                        displayName = userDto.displayName,
                        photoUrl = userDto.photoUrl,
                        friends = userDto.friends,
                        rooms = userDto.rooms
                    )
                )
            )
        }
    }

    override suspend fun getProfile() {
        val response = api.getProfile()
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val body = response.body() ?: throw Exception("response body is null")
        val result = body.result ?: throw Exception("result is null")
        val userDto = result.user
        clientDataStore.updateClient(
            client.copy(
                user = User(
                    id = userDto.id,
                    email = userDto.email,
                    displayName = userDto.displayName,
                    photoUrl = userDto.photoUrl,
                    friends = userDto.friends,
                    rooms = userDto.rooms
                )
            )
        )
    }

    override suspend fun isSigned(): Boolean {
        val client = clientDataStore.dataStoreFlow.firstOrNull()
        if (client?.token != null) {
            return true
        }
        return false
    }

    override suspend fun signOut() {
        clientDataStore.updateClient(
            Client()
        )
    }

    override suspend fun uploadProfileImage(uri: Uri) {
        val inputStream = context.contentResolver.openInputStream(uri) ?: throw Exception("Inputstream is null")
        val requestBody = inputStream?.readBytes()?.toRequestBody(contentType = "image/*".toMediaType())
        val part = MultipartBody.Part.createFormData("profile", "profile", requestBody)
        inputStream.close()

        val response = api.uploadProfileImage(part)
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val result = response.body()?.result ?: throw Exception("reuslt is null")
        clientDataStore.updateClient(
            client.copy(
                user = client.user.copy(photoUrl = result.photoUrl)
            )
        )
    }
}