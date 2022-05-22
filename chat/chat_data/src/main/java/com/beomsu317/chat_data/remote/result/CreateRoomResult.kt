package com.beomsu317.chat_data.remote.result

import com.beomsu317.core.domain.model.Room
import kotlinx.serialization.Serializable

@Serializable
data class CreateRoomResult(
    val room: Room
)
