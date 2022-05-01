package com.beomsu317.privatechatapp.data.remote

import com.beomsu317.privatechatapp.data.remote.dto.UserDto
import com.beomsu317.privatechatapp.data.remote.request.UserLoginRequest
import com.beomsu317.privatechatapp.data.remote.request.UserRegisterRequest
import com.beomsu317.privatechatapp.data.remote.response.PrivateChatResponse
import com.beomsu317.privatechatapp.data.remote.result.GetUserResult
import com.beomsu317.privatechatapp.data.remote.result.UserLoginResult
import retrofit2.Response
import retrofit2.http.*

interface PrivateChatApi {

    @POST("/user/register")
    suspend fun registerUser(@Body request: UserRegisterRequest): Response<PrivateChatResponse<Unit>>

    @POST("/user/login")
    suspend fun loginUser(@Body request: UserLoginRequest): Response<PrivateChatResponse<UserLoginResult>>

    @GET("/user/profile")
    suspend fun getProfile(): Response<PrivateChatResponse<GetUserResult>>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }
}