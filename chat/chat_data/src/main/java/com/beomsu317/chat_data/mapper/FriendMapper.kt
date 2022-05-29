package com.beomsu317.chat_data.mapper

import com.beomsu317.chat_data.local.entity.FriendEntity
import com.beomsu317.core.data.remote.dto.FriendDto
import com.beomsu317.core.domain.model.Friend

fun FriendDto.toEntity(): FriendEntity {
    return FriendEntity(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName
    )
}

fun FriendEntity.toFriend(): Friend {
    return Friend(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName
    )
}