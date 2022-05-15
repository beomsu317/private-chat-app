package com.beomsu317.profile_domain.repository

import android.net.Uri
import com.beomsu317.core.domain.model.User

interface ProfileRepository {

    suspend fun getProfile(token: String): User

    suspend fun signOut()

    suspend fun uploadProfileImage(token: String, uri: Uri): String
}