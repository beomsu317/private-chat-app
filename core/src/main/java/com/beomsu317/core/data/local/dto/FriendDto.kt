package com.beomsu317.privatechatapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FriendDto(
    val id: String,
    val email: String,
    val photoUrl: String,
    val displayName: String
)