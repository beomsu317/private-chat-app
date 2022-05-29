package com.beomsu317.chat_domain.model

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core.domain.model.Message

data class RecentMessage(
    val message: Message,
    val friend: Friend
)
