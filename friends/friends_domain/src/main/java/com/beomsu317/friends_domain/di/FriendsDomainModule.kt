package com.beomsu317.friends_domain.di

import com.beomsu317.friends_domain.repository.FriendsRepository
import com.beomsu317.friends_domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FriendsDomainModule {

    @Provides
    @Singleton
    fun provideFriendsUseCases(repository: FriendsRepository): FriendsUseCases {
        return FriendsUseCases(
            addFriendUseCase = AddFriendUseCase(repository),
            deleteFriendUseCase = DeleteFriendUseCase(repository),
            getAllFriendsUseCase = GetAllFriendsUseCase(repository),
            getMyFriendsUseCase = GetMyFriendsUseCase(repository),
            searchFriendsUseCase = SearchFriendsUseCase(repository)
        )
    }
}