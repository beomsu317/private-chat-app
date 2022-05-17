package com.beomsu317.profile_domain.di

import com.beomsu317.profile_domain.repository.ProfileRepository
import com.beomsu317.profile_domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileDomainModule {

    @Provides
    @Singleton
    fun provideProfileUseCases(
        repository: ProfileRepository
    ): ProfileUseCases {
        return ProfileUseCases(
            getProfileUseCase = GetProfileUseCase(repository),
            signOutUseCase = SignOutUseCase(repository),
            uploadProfileImageUseCase = UploadProfileImageUseCase(repository),
        )
    }
}