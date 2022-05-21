package com.beomsu317.friends_domain.model

data class FriendWithPriority(
    val id: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val displayName: String = "",
    val priority: Int = 0
)
