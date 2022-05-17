package com.beomsu317.startup_data.remote

import com.beomsu317.core.data.remote.response.PrivateChatResponse
import com.beomsu317.startup_data.remote.request.UserSignInRequest
import com.beomsu317.startup_data.remote.request.UserRegisterRequest
import com.beomsu317.startup_data.remote.result.UserSignInResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PrivateChatApi {

    @POST("/user/register")
    suspend fun registerUser(@Body request: UserRegisterRequest): Response<PrivateChatResponse<Unit>>

    @POST("/user/login")
    suspend fun signInUser(@Body request: UserSignInRequest): Response<PrivateChatResponse<UserSignInResult>>
}