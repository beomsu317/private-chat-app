package com.beomsu317.privatechatapp.data.remote.result

import kotlinx.serialization.Serializable

@Serializable
data class UploadProfileImageResult(
    val photoUrl: String
)