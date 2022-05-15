package com.beomsu317.friends_presentation.friends_list

import com.beomsu317.core.domain.model.Friend

data class FriendsListState(
    val isLoading: Boolean = false,
    val friends: Set<Friend> = emptySet()
)