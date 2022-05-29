package com.beomsu317.friends_domain.use_case

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core.domain.model.UserFriend
import com.beomsu317.friends_domain.model.FriendWithPriority

class SortByPriorityUseCase {
    suspend operator fun invoke(
        userFriends: Set<UserFriend>,
        friends: Set<Friend>
    ): List<FriendWithPriority> {
        if (friends.isNullOrEmpty()) {
            return emptyList()
        }
        val sortedUserFriend = userFriends.sortedBy {
            it.priority
        }.reversed()
        var friendsList = friends.toList()
        val sortedFriendsList = mutableListOf<FriendWithPriority>()
        sortedUserFriend.forEach { userFriend ->
            friendsList.forEachIndexed { index, friend ->
                if (userFriend.id == friend.id) {
                    sortedFriendsList += FriendWithPriority(
                        id = friendsList[index].id,
                        email = friendsList[index].email,
                        photoUrl = friendsList[index].photoUrl,
                        displayName = friendsList[index].displayName,
                        priority = userFriend.priority,
                        numberOfFriends = friendsList[index].numberOfFriends,
                        numberOfRooms = friendsList[index].numberOfRooms
                    )
                    friendsList = friendsList - friendsList.get(index)
                    return@forEachIndexed
                }
            }
        }
        return sortedFriendsList
    }
}