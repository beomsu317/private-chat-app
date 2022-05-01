package com.beomsu317.privatechatapp.di

import com.beomsu317.privatechatapp.domain.model.Client
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideClient(): Client {
        return Client()
    }
}