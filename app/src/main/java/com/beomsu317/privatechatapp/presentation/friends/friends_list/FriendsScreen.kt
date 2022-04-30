package com.beomsu317.privatechatapp.presentation.friends

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun FriendsListScreen(
    navHostController: NavHostController
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Friends List Screen",
            modifier = Modifier.clickable {
                navHostController.navigate(FriendsScreen.FriendProfileScreen.route)
            }
        )
    }
}