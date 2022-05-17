package com.beomsu317.friends_data.di

import android.content.Context
import androidx.room.Room
import com.beomsu317.core.common.Constants
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.friends_data.remote.PrivateChatApi
import com.beomsu317.friends_data.repository.FriendsRepositoryImpl
import com.beomsu317.friends_domain.repository.FriendsRepository
import com.beomsu317.friends_data.local.PrivateChatDatabase
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
object FriendsDataModule {

    @Provides
    @Singleton
    fun providePrivateChatApi(): PrivateChatApi {
        return Retrofit.Builder()
            .baseUrl(Constants.PRIVATE_CHAT_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PrivateChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFriendsRepository(api: PrivateChatApi, database: PrivateChatDatabase, appDataStore: AppDataStore): FriendsRepository {
        return FriendsRepositoryImpl(api, database, appDataStore)
    }

    @Provides
    @Singleton
    fun providePrivateChatDatabase(@ApplicationContext context: Context): PrivateChatDatabase {
        return Room.databaseBuilder(
            context,
            PrivateChatDatabase::class.java,
            "private_chat.db"
        ).build()
    }
}