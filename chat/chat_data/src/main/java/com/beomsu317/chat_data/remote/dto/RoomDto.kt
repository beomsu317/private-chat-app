package com.beomsu317.chat_data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RoomDto(
    val id: String,
    val owner: String,
    val users: Set<String>,
)
