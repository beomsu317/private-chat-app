package com.beomsu317.chat_data.mapper

import com.beomsu317.chat_data.local.entity.RoomEntity
import com.beomsu317.chat_data.remote.dto.RoomDto
import com.beomsu317.core.domain.model.Room

fun RoomDto.toRoom(): Room {
    return Room(
        id = id,
        owner = owner,
        users = users
    )
}

fun RoomEntity.toRoom(): Room {
    return Room(
        id = id,
        owner = owner,
        users = users
    )
}

fun RoomDto.toEntity(): RoomEntity {
    return RoomEntity(
        id = id,
        owner = owner,
        users = users
    )
}