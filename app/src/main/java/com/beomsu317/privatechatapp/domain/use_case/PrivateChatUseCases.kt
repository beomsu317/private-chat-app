package com.beomsu317.privatechatapp.domain.use_case

data class PrivateChatUseCases(
    val signUpUseCase: SignUpUseCase,
    val signInUseCase: SignInUseCase,
    val getProfileUseCase: GetProfileUseCase,
    val isSignedInUseCase: IsSignedInUseCase
)