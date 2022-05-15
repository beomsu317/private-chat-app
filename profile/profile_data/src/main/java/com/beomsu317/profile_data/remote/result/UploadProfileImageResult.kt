package com.beomsu317.profile_data.remote.result

import kotlinx.serialization.Serializable

@Serializable
data class UploadProfileImageResult(
    val photoUrl: String
)