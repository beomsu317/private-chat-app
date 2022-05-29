package com.beomsu317.friends_data.mapper

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.friends_data.local.entity.UserFriendEntity
import com.beomsu317.friends_data.remote.dto.FriendDto

fun Friend.toDto(): FriendDto {
    return FriendDto(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName,
        numberOfFriends = numberOfFriends,
        numberOfRooms = numberOfRooms
    )
}

fun FriendDto.toFriend(): Friend {
    return Friend(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName,
        numberOfFriends = numberOfFriends,
        numberOfRooms = numberOfRooms
    )
}

fun UserFriendEntity.toFriend(): Friend {
    return Friend(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName,
        numberOfFriends = numberOfFriends,
        numberOfRooms = numberOfRooms
    )
}

fun FriendDto.toUserFriendEntity(): UserFriendEntity {
    return UserFriendEntity(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName,
        numberOfFriends = numberOfFriends,
        numberOfRooms = numberOfRooms
    )
}

fun Friend.toUserFriendEntity(): UserFriendEntity {
    return UserFriendEntity(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName,
        numberOfFriends = numberOfFriends,
        numberOfRooms = numberOfRooms
    )
}