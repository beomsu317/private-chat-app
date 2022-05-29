package com.beomsu317.friends_data.remote

import com.beomsu317.core.data.remote.response.PrivateChatResponse
import com.beomsu317.friends_data.remote.request.AddFriendRequest
import com.beomsu317.friends_data.remote.request.DeleteFriendRequest
import com.beomsu317.friends_data.remote.request.GetSearchFriendsRequest
import com.beomsu317.friends_data.remote.result.AddFriendResult
import com.beomsu317.friends_data.remote.result.GetFriendsResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PrivateChatApi {

    @GET("/user/friends")
    suspend fun getMyFriends(@Header("Authorization") auth: String): Response<PrivateChatResponse<GetFriendsResult>>

    @POST("/user/friends/search")
    suspend fun getAllFriends(@Header("Authorization") auth: String, @Body request: GetSearchFriendsRequest): Response<PrivateChatResponse<GetFriendsResult>>

    @POST("/user/friends/add")
    suspend fun addFriend(@Header("Authorization") auth: String, @Body request: AddFriendRequest): Response<PrivateChatResponse<AddFriendResult>>

    @POST("/user/friends/delete")
    suspend fun deleteFriend(@Header("Authorization") auth: String, @Body request: DeleteFriendRequest): Response<PrivateChatResponse<Unit>>
}