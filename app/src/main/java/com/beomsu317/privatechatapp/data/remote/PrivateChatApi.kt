package com.beomsu317.privatechatapp.data.remote

import com.beomsu317.privatechatapp.data.remote.dto.UserDto
import com.beomsu317.privatechatapp.data.remote.request.UserLoginRequest
import com.beomsu317.privatechatapp.data.remote.request.UserRegisterRequest
import com.beomsu317.privatechatapp.data.remote.response.PrivateChatResponse
import com.beomsu317.privatechatapp.data.remote.result.GetUserResult
import com.beomsu317.privatechatapp.data.remote.result.UploadProfileImageResult
import com.beomsu317.privatechatapp.data.remote.result.UserLoginResult
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PrivateChatApi {

    @POST("/user/register")
    suspend fun registerUser(@Body request: UserRegisterRequest): Response<PrivateChatResponse<Unit>>

    @POST("/user/login")
    suspend fun loginUser(@Body request: UserLoginRequest): Response<PrivateChatResponse<UserLoginResult>>

    @GET("/user/profile")
    suspend fun getProfile(): Response<PrivateChatResponse<GetUserResult>>

    @Multipart
    @POST("/user/profile/upload-image")
    suspend fun uploadProfileImage(@Part image: MultipartBody.Part): Response<PrivateChatResponse<UploadProfileImageResult>>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }
}