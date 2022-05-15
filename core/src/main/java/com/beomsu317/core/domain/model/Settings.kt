package com.beomsu317.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val notifications: Boolean = true
)
