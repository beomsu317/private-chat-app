package com.beomsu317.privatechatapp.di

import android.content.Context
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.data.local.data_store.AppDataStoreImpl
import com.beomsu317.core.data.repository.AppRepositoryImpl
import com.beomsu317.core.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDataStore(@ApplicationContext context: Context): AppDataStore =
        AppDataStoreImpl(context, Dispatchers.IO)

    @Singleton
    @Provides
    fun provideAppRepository(
        appDataStore: AppDataStore
    ): AppRepository {
        return AppRepositoryImpl(appDataStore, Dispatchers.IO)
    }
}