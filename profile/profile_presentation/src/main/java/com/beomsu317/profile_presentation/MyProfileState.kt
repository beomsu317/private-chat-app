package com.beomsu317.profile_presentation

import com.beomsu317.core.domain.model.User


data class MyProfileState(
    val isLoading: Boolean = false,
    val user: User = User(),
)
