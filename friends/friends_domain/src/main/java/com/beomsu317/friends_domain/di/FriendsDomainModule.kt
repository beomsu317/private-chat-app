package com.beomsu317.friends_domain.di

import com.beomsu317.core.domain.repository.CoreRepository
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
    fun provideFriendsUseCases(friendsRepository: FriendsRepository, coreRepository: CoreRepository): FriendsUseCases {
        return FriendsUseCases(
            addFriendUseCase = AddFriendUseCase(friendsRepository),
            deleteFriendUseCase = DeleteFriendUseCase(friendsRepository),
            searchFriendsUseCase = SearchFriendsUseCase(friendsRepository),
            getUserFriendsUseCase = GetUserFriendsUseCase(friendsRepository),
            sortByPriorityUseCase = SortByPriorityUseCase(),
            updateUserUseCase = UpdateUserUseCase(coreRepository),
            searchUserFriendUseCase = SearchUserFriendUseCase(friendsRepository),
        )
    }
}