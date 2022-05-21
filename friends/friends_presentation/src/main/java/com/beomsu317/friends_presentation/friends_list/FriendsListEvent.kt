package com.beomsu317.friends_presentation.friends_list

import com.beomsu317.friends_domain.model.FriendWithPriority

sealed class FriendsListEvent {
    data class RefreshFriends(val refresh: Boolean): FriendsListEvent()
    object Search: FriendsListEvent()
    data class DeleteFriend(val friendId: String): FriendsListEvent()
    data class UpdateUser(val friendWithPriority: FriendWithPriority): FriendsListEvent()
}