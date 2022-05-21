package com.beomsu317.friends_presentation.friends_list

import com.beomsu317.friends_domain.model.FriendWithPriority

data class FriendsListState(
    val isLoading: Boolean = false,
    val friends: List<FriendWithPriority> = emptyList(),
)