package com.beomsu317.friends_presentation.friends_list

import com.beomsu317.core.domain.model.Friend


sealed class FriendsListEvent {
    data class RefreshFriends(val refresh: Boolean): FriendsListEvent()
    data class Search(val searchText: String): FriendsListEvent()
    data class DeleteFriend(val friend: Friend): FriendsListEvent()
}