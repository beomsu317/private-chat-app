package com.beomsu317.profile_data.remote

import com.beomsu317.core.data.remote.response.PrivateChatResponse
import com.beomsu317.profile_data.remote.result.GetUserResult
import com.beomsu317.profile_data.remote.result.UploadProfileImageResult
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PrivateChatApi {

    @GET("/user/profile")
    suspend fun getProfile(@Header("Authorization") auth: String): Response<PrivateChatResponse<GetUserResult>>

    @Multipart
    @POST("/user/profile/upload-image")
    suspend fun uploadProfileImage(@Header("Authorization") auth: String, @Part image: MultipartBody.Part): Response<PrivateChatResponse<UploadProfileImageResult>>
}