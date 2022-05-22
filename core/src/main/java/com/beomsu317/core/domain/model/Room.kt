package com.beomsu317.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val id: String = "",
    val owner: String = "",
    val users: Set<String> = emptySet(),
)
