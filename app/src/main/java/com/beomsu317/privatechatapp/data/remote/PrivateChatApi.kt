package com.beomsu317.privatechatapp.data.remote

import com.beomsu317.privatechatapp.data.remote.request.AddFriendRequest
import com.beomsu317.privatechatapp.data.remote.request.DeleteFriendRequest
import com.beomsu317.core.data.remote.response.PrivateChatResponse
import com.beomsu317.privatechatapp.data.remote.request.UserLoginRequest
import com.beomsu317.privatechatapp.data.remote.request.UserRegisterRequest
import com.beomsu317.privatechatapp.data.remote.result.*
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

    @GET("/user/friends")
    suspend fun getFriends(): Response<PrivateChatResponse<GetFriendsResult>>

    @GET("/user/friends/all")
    suspend fun getAllFriends(): Response<PrivateChatResponse<GetFriendsResult>>

    @POST("/user/friends/add")
    suspend fun addFriend(@Body request: AddFriendRequest): Response<PrivateChatResponse<AddFriendResult>>

    @POST("/user/friends/delete")
    suspend fun deleteFriend(@Body request: DeleteFriendRequest): Response<PrivateChatResponse<Unit>>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }
}