package com.beomsu317.chat_domain.di

import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.chat_domain.use_case.*
import com.beomsu317.core.domain.repository.CoreRepository
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
    fun provideChatUseCases(chatRepository: ChatRepository, coreRepository: CoreRepository): ChatUseCases {
        return ChatUseCases(
            createRoomUseCase = CreateRoomUseCase(chatRepository),
            getLastMessagesFlowUseCase = GetLastMessagesFlowUseCase(chatRepository),
            getMessagesFlowUseCase = GetMessagesFlowUseCase(chatRepository),
            getFriendUseCase = GetFriendUseCase(chatRepository),
            sendMessageUseCase = SendMessageUseCase(chatRepository),
            readAllMessagesUseCase = ReadAllMessagesUseCase(chatRepository),
            getRecentMessagesUseCase = GetRecentMessagesUseCase(chatRepository, coreRepository = coreRepository),
            leaveRoomUseCase = LeaveRoomUseCase(chatRepository),
            connectToServerUseCase = ConnectToServerUseCase(chatRepository, coreRepository),
            removeMessagesUseCase = RemoveMessagesUseCase(chatRepository)
        )
    }


}