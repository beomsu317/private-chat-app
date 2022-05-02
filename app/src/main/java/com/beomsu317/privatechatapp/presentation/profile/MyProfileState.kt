package com.beomsu317.privatechatapp.presentation.profile

import com.beomsu317.privatechatapp.domain.model.User

data class MyProfileState(
    val isLoading: Boolean = false,
    val user: User = User(),
)
