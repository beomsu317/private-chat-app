package com.beomsu317.chat_data.remote

import com.beomsu317.chat_data.remote.request.CreateRoomRequest
import com.beomsu317.chat_data.remote.result.CreateRoomResult
import com.beomsu317.core.data.remote.response.PrivateChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PrivateChatApi {

    @POST("/chat/room/create")
    suspend fun createRoom(@Header("Authorization") auth: String, @Body request: CreateRoomRequest): Response<PrivateChatResponse<CreateRoomResult>>
}