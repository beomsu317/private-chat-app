package com.beomsu317.privatechatapp.domain.model

data class User(
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String = "",
    val friends: Set<String> = emptySet(),
    val rooms: Set<String> = emptySet()
)