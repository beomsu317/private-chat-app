package com.beomsu317.startup_domain.use_case

import javax.inject.Inject

class StartupUseCases @Inject constructor(
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase
)