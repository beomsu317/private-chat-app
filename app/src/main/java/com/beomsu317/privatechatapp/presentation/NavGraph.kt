package com.beomsu317.privatechatapp.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.beomsu317.privatechatapp.R
import com.beomsu317.privatechatapp.presentation.startup.startupNavGraph
import com.beomsu317.privatechatapp.presentation.chat.chatNavGraph
import com.beomsu317.privatechatapp.presentation.friends.friendsNavGraph
import com.beomsu317.privatechatapp.presentation.profile.profileNavGraph

const val ROOT_GRAPH_ROUTE = "root"
const val STARTUP_GRAPH_ROUTE = "startup"
const val FRIENDS_GRAPH_ROUTE = "friends"
const val CHAT_GRAPH_ROUTE = "chat"
const val PROFILE_GRAPH_ROUTE = "profile"

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    innerPadding: PaddingValues,
    scaffoldState: ScaffoldState
) {
    NavHost(
        navController = navHostController,
        startDestination = STARTUP_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE,
        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
    ) {
        startupNavGraph(navHostController, scaffoldState)
        friendsNavGraph(navHostController)
        chatNavGraph(navHostController)
        profileNavGraph(navHostController)
    }
}

sealed class BottomNavRoute(
    val route: String,
    @DrawableRes val selectedResId: Int,
    @DrawableRes val unselectedResId: Int,
    val label: String
) {
    object FriendsRoute : BottomNavRoute(
        route = FRIENDS_GRAPH_ROUTE,
        selectedResId = R.drawable.ic_baseline_people_24,
        unselectedResId = R.drawable.ic_baseline_people_outline_24,
        "Friends"
    )

    object ChatRoute : BottomNavRoute(
        route = CHAT_GRAPH_ROUTE,
        selectedResId = R.drawable.ic_baseline_chat_bubble_24,
        unselectedResId = R.drawable.ic_baseline_chat_bubble_outline_24,
        "Chat"
    )

    object ProfileRoute : BottomNavRoute(
        route = PROFILE_GRAPH_ROUTE,
        selectedResId = R.drawable.ic_baseline_person_24,
        unselectedResId = R.drawable.ic_outline_person_24,
        "Profile"
    )
}

val bottomNavBarRoutes = listOf(
    BottomNavRoute.FriendsRoute,
    BottomNavRoute.ChatRoute,
    BottomNavRoute.ProfileRoute
)