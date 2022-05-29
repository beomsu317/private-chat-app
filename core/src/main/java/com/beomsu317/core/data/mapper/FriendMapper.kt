package com.beomsu317.core.data.mapper

import com.beomsu317.core.data.remote.dto.FriendDto
import com.beomsu317.core.domain.model.Friend

fun FriendDto.toFriend(): Friend {
    return Friend(
        id = id,
        email = email,
        displayName = displayName,
        photoUrl = photoUrl
    )
}