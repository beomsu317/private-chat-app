package com.beomsu317.core.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class PrivateChatResponse<T>(
    val code: String? = null,
    val message: String? = null,
    val result: T? = null
)