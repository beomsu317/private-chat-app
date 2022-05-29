package com.beomsu317.privatechatapp.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.beomsu317.chat_presentation.chatNavGraph
import com.beomsu317.core_ui.navigation.startupNavGraph
import com.beomsu317.friends_presentation.friendsNavGraph
import com.beomsu317.privatechatapp.navigation.bottom_navigation.ROOT_GRAPH_ROUTE
import com.beomsu317.privatechatapp.navigation.bottom_navigation.STARTUP_GRAPH_ROUTE
import com.beomsu317.profile_presentation.profileNavGraph

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    innerPadding: PaddingValues,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = STARTUP_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE,
        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
    ) {
        startupNavGraph(navHostController, showSnackbar)
        friendsNavGraph(navHostController, showSnackbar)
        chatNavGraph(navHostController, showSnackbar)
        profileNavGraph(navHostController, showSnackbar)
    }
}
