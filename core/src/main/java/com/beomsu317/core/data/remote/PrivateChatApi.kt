package com.beomsu317.core.data.remote

import com.beomsu317.core.data.remote.request.UpdateUserRequest
import com.beomsu317.core.data.remote.response.PrivateChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PrivateChatApi {

    @POST("/user/profile/update")
    suspend fun updateUser(@Header("Authorization") auth: String, @Body request: UpdateUserRequest): Response<PrivateChatResponse<Unit>>
}