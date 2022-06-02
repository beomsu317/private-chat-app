package com.beomsu317.chat_data.remote

import com.beomsu317.chat_data.remote.request.CreateRoomRequest
import com.beomsu317.chat_data.remote.request.LeaveRoomRequest
import com.beomsu317.chat_data.remote.result.CreateRoomResult
import com.beomsu317.chat_data.remote.result.GetFriendResult
import com.beomsu317.chat_data.remote.result.GetRoomInfoResult
import com.beomsu317.core.data.remote.response.PrivateChatResponse
import retrofit2.Response
import retrofit2.http.*

interface PrivateChatApi {

    @POST("/chat/room/create")
    suspend fun createRoom(@Header("Authorization") auth: String, @Body request: CreateRoomRequest): Response<PrivateChatResponse<CreateRoomResult>>

    @GET("/chat/room/info/{roomId}")
    suspend fun getRoomInfo(@Header("Authorization") auth: String, @Path("roomId") roomId: String): Response<PrivateChatResponse<GetRoomInfoResult>>

    @GET("/user/friends/{friendId}")
    suspend fun getFriend(@Header("Authorization") auth: String, @Path("friendId") friendId: String): Response<PrivateChatResponse<GetFriendResult>>

    @POST("/chat/room/leave")
    suspend fun leaveRoom(@Header("Authorization") auth: String, @Body request: LeaveRoomRequest): Response<PrivateChatResponse<Unit>>

}