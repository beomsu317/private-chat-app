package com.beomsu317.privatechatapp.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.beomsu317.privatechatapp.presentation.profile.main_profile.MainProfileViewModel

@Composable
fun MainProfileScreen(
    viewModel: MainProfileViewModel = hiltViewModel()
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "MainProfileScreen"
        )
    }
}