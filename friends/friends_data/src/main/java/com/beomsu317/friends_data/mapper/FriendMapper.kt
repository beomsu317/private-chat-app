package com.beomsu317.friends_data.mapper

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.friends_data.local.entity.FriendEntity
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

fun FriendEntity.toFriend(): Friend {
    return Friend(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName,
        numberOfFriends = numberOfFriends,
        numberOfRooms = numberOfRooms
    )
}

fun FriendDto.toEntity(): FriendEntity {
    return FriendEntity(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName,
        numberOfFriends = numberOfFriends,
        numberOfRooms = numberOfRooms
    )
}

fun Friend.toEntity(): FriendEntity {
    return FriendEntity(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName,
        numberOfFriends = numberOfFriends,
        numberOfRooms = numberOfRooms
    )
}