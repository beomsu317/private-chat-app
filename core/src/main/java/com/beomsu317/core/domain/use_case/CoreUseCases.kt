package com.beomsu317.core.domain.use_case

data class CoreUseCases(
    val getSettingsFlowUseCase: GetSettingsFlowUseCase,
    val getTokenFlowUseCase: GetTokenFlowUseCase,
    val getUserFlowUseCase: GetUserFlowUseCase,
    val updateSettingsUseCase: UpdateSettingsUseCase,
    val updateTokenUseCase: UpdateTokenUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase
)
