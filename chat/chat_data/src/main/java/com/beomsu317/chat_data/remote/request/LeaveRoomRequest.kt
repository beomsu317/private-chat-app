package com.beomsu317.chat_data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class LeaveRoomRequest(
    val roomId: String
)