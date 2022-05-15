package com.beomsu317.friends_presentation.add_friends

import com.beomsu317.core.domain.model.Friend

data class AddFriendsState(
    val isLoading: Boolean = false,
    val friends: Set<Friend> = emptySet(),
)
