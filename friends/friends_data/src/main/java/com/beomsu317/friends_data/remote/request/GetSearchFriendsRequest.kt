package com.beomsu317.friends_data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class GetSearchFriendsRequest(
    val searchText: String
)
