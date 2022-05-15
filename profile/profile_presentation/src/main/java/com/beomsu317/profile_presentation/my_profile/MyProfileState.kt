package com.beomsu317.profile_presentation.my_profile

import com.beomsu317.core.domain.model.User


data class MyProfileState(
    val isLoading: Boolean = false,
    val user: User = User(),
)
