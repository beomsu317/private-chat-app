package com.beomsu317.startup_data.di

import com.beomsu317.core.common.Constants
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.startup_data.remote.PrivateChatApi
import com.beomsu317.startup_data.repository.StartupRepositoryImpl
import com.beomsu317.startup_domain.repository.StartupRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StartupDataModule {

    @Singleton
    @Provides
    fun providePrivateChatApi(): PrivateChatApi {
        return Retrofit.Builder()
            .baseUrl(Constants.PRIVATE_CHAT_BASE_URL)
            .addConverterFactory(
                Json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(PrivateChatApi::class.java)
    }

    @Singleton
    @Provides
    fun provideStartupRepository(
        api: PrivateChatApi,
        appDataStore: AppDataStore
    ): StartupRepository {
        return StartupRepositoryImpl(api, appDataStore, Dispatchers.IO)
    }
}