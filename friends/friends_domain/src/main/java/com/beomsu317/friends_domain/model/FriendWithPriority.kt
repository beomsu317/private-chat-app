package com.beomsu317.friends_domain.model

import com.beomsu317.core.domain.model.Friend

data class FriendWithPriority(
    val id: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val displayName: String = "",
    val priority: Int = 0,
    val numberOfFriends: Int = 0,
    val numberOfRooms: Int = 0
)

fun FriendWithPriority.toFriend(): Friend {
    return Friend(
        id = id,
        email = email,
        photoUrl = photoUrl,
        displayName = displayName,
        numberOfFriends = numberOfFriends,
        numberOfRooms = numberOfRooms
    )
}