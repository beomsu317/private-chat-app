package com.beomsu317.profile_domain.use_case

data class ProfileUseCases(
    val getProfileUseCase: GetProfileUseCase,
    val signOutUseCase: SignOutUseCase,
    val uploadProfileImageUseCase: UploadProfileImageUseCase
)