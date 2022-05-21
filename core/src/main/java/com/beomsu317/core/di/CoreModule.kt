package com.beomsu317.core.di

import android.content.Context
import com.beomsu317.core.common.Constants
import com.beomsu317.core.data.local.data_store.AppDataStoreImpl
import com.beomsu317.core.data.remote.PrivateChatApi
import com.beomsu317.core.data.repository.CoreRepositoryImpl
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.domain.repository.CoreRepository
import com.beomsu317.core.domain.use_case.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideAppDataStore(@ApplicationContext context: Context): AppDataStore =
        AppDataStoreImpl(context, Dispatchers.IO)

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
    fun providePrivateChatRepository(api: PrivateChatApi, appDataStore: AppDataStore): CoreRepository {
        return CoreRepositoryImpl(api, appDataStore)
    }

    @Provides
    @Singleton
    fun provideCoreUseCases(coreRepository: CoreRepository): CoreUseCases {
        return CoreUseCases(
            getSettingsFlowUseCase = GetSettingsFlowUseCase(coreRepository),
            getTokenFlowUseCase = GetTokenFlowUseCase(coreRepository),
            getUserFlowUseCase = GetUserFlowUseCase(coreRepository),
            updateSettingsUseCase = UpdateSettingsUseCase(coreRepository),
            updateTokenUseCase = UpdateTokenUseCase(coreRepository),
            validateEmailUseCase = ValidateEmailUseCase(),
            validatePasswordUseCase = ValidatePasswordUseCase()
        )
    }
}