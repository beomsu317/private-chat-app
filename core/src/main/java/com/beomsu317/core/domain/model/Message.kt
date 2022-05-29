package com.beomsu317.core.domain.model

data class Message(
    val senderId: String,
    val roomId: String,
    val timestamp: Long,
    val displayName: String,
    val message: String,
    val photoUrl: String,
    val read: Boolean = false
)
