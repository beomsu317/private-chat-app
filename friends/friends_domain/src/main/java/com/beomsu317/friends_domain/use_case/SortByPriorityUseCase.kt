package com.beomsu317.friends_domain.use_case

import android.util.Log
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
        val reversedUserFriend = userFriends.sortedBy {
            it.priority
        }.reversed()
        var friendsList = friends.toList()
        val sortedFriendList = mutableListOf<FriendWithPriority>()
        reversedUserFriend.forEach { userFriend ->
            friendsList.forEachIndexed { index, friend ->
                if (userFriend.id == friend.id) {
                    sortedFriendList += FriendWithPriority(
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
        return sortedFriendList
    }
}