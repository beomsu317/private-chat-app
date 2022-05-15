package com.beomsu317.core.domain.repository

interface AppRepository {

    suspend fun getToken(): String?
}