package com.beomsu317.privatechatapp.domain.model

data class Client(
    var token: String? = null,
    var user: User = User()
)
