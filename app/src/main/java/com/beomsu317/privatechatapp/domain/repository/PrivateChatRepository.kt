package com.beomsu317.privatechatapp.domain.repository

import android.graphics.Bitmap
import android.net.Uri

interface PrivateChatRepository {

    suspend fun registerUser(displayName: String, email: String, password: String, confirmPassword: String)

    suspend fun loginUser(email: String, password: String)

    suspend fun getProfile()

    suspend fun isSigned(): Boolean

    suspend fun signOut()

    suspend fun uploadProfileImage(uri: Uri)
}