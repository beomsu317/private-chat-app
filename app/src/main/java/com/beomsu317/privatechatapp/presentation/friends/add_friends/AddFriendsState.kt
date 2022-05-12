package com.beomsu317.privatechatapp.presentation.friends.add_friends

import com.beomsu317.privatechatapp.domain.model.Friend

data class AddFriendsState(
    val isLoading: Boolean = false,
    val friends: Set<Friend> = emptySet(),
)
