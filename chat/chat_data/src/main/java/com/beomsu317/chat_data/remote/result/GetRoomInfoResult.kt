package com.beomsu317.chat_data.remote.result

import com.beomsu317.chat_data.remote.dto.RoomDto
import kotlinx.serialization.Serializable

@Serializable
data class GetRoomInfoResult(
    val room: RoomDto
)
