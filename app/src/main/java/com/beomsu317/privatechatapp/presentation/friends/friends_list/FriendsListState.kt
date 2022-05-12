package com.beomsu317.privatechatapp.presentation.friends.friends_list

import com.beomsu317.privatechatapp.domain.model.Friend
import com.beomsu317.privatechatapp.domain.model.User

data class FriendsListState(
    val isLoading: Boolean = false,
    val friends: Set<Friend> = emptySet()
)