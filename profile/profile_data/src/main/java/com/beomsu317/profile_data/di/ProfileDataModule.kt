package com.beomsu317.profile_data.di

import android.content.Context
import com.beomsu317.core.common.Constants
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.profile_data.remote.PrivateChatApi
import com.beomsu317.profile_data.repository.ProfileRepositoryImpl
import com.beomsu317.profile_domain.repository.ProfileRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileDataModule {

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

    @Provides
    @Singleton
    fun provideProfileRepository(api: PrivateChatApi, @ApplicationContext context: Context, appDataStore: AppDataStore): ProfileRepository {
        return ProfileRepositoryImpl(api, context, appDataStore)
    }
}