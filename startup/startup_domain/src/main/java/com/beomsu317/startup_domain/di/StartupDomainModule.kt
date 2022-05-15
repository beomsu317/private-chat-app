package com.beomsu317.startup_domain.di

import com.beomsu317.startup_domain.repository.StartupRepository
import com.beomsu317.startup_domain.use_case.SignInUseCase
import com.beomsu317.startup_domain.use_case.SignUpUseCase
import com.beomsu317.startup_domain.use_case.StartupUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StartupDomainModule {

    @Provides
    @Singleton
    fun provideStartupUseCases(
        repository: StartupRepository
    ): StartupUseCases = StartupUseCases(
        signInUseCase = SignInUseCase(repository),
        signUpUseCase = SignUpUseCase(repository)
    )
}