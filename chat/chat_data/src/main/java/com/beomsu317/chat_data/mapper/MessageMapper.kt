package com.beomsu317.chat_data.mapper

import com.beomsu317.chat_data.local.entity.MessageEntity
import com.beomsu317.chat_data.remote.dto.MessageDto
import com.beomsu317.core.domain.model.Message

fun Message.toEntity(): MessageEntity {
    return MessageEntity(
        senderId = senderId,
        roomId = roomId,
        timestamp = timestamp,
        displayName = displayName,
        message = message,
        photoUrl = photoUrl,
        read = read
    )
}

fun MessageDto.toMessage(read: Boolean): Message {
    return Message(
        senderId = senderId,
        roomId = roomId,
        timestamp = timestamp,
        displayName = displayName,
        message = message,
        photoUrl = photoUrl,
        read = read
    )
}

fun Message.toDto(): MessageDto {
    return MessageDto(
        senderId = senderId,
        roomId = roomId,
        timestamp = timestamp,
        displayName = displayName,
        message = message,
        photoUrl = photoUrl
    )
}