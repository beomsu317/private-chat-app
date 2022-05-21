package com.beomsu317.core.domain.repository

import com.beomsu317.core.domain.model.Settings
import com.beomsu317.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface CoreRepository {

    suspend fun updateUser(user: User)

    suspend fun updateSettings(settings: Settings)

    suspend fun updateToken(token: String)

    fun getUserFlow(): Flow<User>

    fun getSettingsFlow(): Flow<Settings>

    fun getTokenFlow(): Flow<String>
}