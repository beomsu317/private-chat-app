package com.beomsu317.chat_domain.di

import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.chat_domain.use_case.ChatUseCases
import com.beomsu317.chat_domain.use_case.CreateRoomUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatDomainModule {

    @Provides
    @Singleton
    fun provideChatUseCases(chatRepository: ChatRepository): ChatUseCases {
        return ChatUseCases(
            createRoomUseCase = CreateRoomUseCase(chatRepository)
        )
    }
}