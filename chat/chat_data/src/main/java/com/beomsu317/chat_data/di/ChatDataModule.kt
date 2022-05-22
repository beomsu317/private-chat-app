package com.beomsu317.chat_data.di

import com.beomsu317.chat_data.remote.PrivateChatApi
import com.beomsu317.chat_data.repository.ChatRepositoryImpl
import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.core.common.Constants
import com.beomsu317.core.domain.data_store.AppDataStore
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
object ChatDataModule {

    @Provides
    @Singleton
    fun providePrivateApi(): PrivateChatApi {
        return Retrofit.Builder()
            .baseUrl(Constants.PRIVATE_CHAT_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PrivateChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChatRepository(api: PrivateChatApi, appDataStore: AppDataStore): ChatRepository {
        return ChatRepositoryImpl(api, appDataStore, Dispatchers.IO)
    }
}