package com.beomsu317.core.data.mapper

import com.beomsu317.core.domain.model.User
import com.beomsu317.core.domain.model.UserFriend
import com.beomsu317.privatechatapp.data.remote.dto.UserDto
import com.beomsu317.privatechatapp.data.remote.dto.UserFriendDto


fun UserDto.toUser(): User {
    return User(
        id = id,
        email = email,
        displayName = displayName,
        photoUrl = photoUrl,
        friends = friends.map { it.toUserFriend() }.toSet(),
        rooms = rooms
    )
}

fun UserFriendDto.toUserFriend(): UserFriend {
    return UserFriend(
        id = id,
        priority = priority
    )
}

fun UserFriend.toDto(): UserFriendDto {
    return UserFriendDto(
        id = id,
        priority = priority
    )
}