package com.beomsu317.chat_data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val senderId: String,
    val roomId: String,
    val timestamp: Long,
    val displayName: String,
    val message: String,
    val photoUrl: String
)
