package com.beomsu317.privatechatapp.di

import com.beomsu317.privatechatapp.data.remote.PrivateChatApi
import com.beomsu317.privatechatapp.data.repository.PrivateChatRepositoryImpl
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePrivateChatApi(): PrivateChatApi {
        return Retrofit.Builder()
            .baseUrl(PrivateChatApi.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
            .create(PrivateChatApi::class.java)
    }

    @Provides
    @Singleton
    fun providePrivateChatRepository(api: PrivateChatApi): PrivateChatRepository =
        PrivateChatRepositoryImpl(api)
}