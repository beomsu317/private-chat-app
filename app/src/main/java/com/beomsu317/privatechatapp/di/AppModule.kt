package com.beomsu317.privatechatapp.di

import android.content.Context
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.data.local.data_store.AppDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDataStore(@ApplicationContext context: Context): AppDataStore =
        AppDataStoreImpl(context, Dispatchers.IO)
}