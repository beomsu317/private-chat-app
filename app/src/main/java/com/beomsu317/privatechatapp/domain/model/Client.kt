package com.beomsu317.privatechatapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Client(
    var token: String? = null,
    var user: User = User()
)
