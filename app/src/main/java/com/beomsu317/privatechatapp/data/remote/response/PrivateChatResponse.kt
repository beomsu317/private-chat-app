package com.beomsu317.privatechatapp.data.remote.response

@kotlinx.serialization.Serializable
data class PrivateChatResponse<T>(
    val code: String? = null,
    val message: String? = null,
    val result: T? = null
)