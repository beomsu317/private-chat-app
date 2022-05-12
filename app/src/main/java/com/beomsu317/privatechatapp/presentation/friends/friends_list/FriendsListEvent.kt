package com.beomsu317.privatechatapp.presentation.friends.friends_list

import com.beomsu317.privatechatapp.domain.model.Friend

sealed class FriendsListEvent {
    data class RefreshFriends(val refresh: Boolean): FriendsListEvent()
    data class Search(val searchText: String): FriendsListEvent()
    data class DeleteFriend(val friend: Friend): FriendsListEvent()
}