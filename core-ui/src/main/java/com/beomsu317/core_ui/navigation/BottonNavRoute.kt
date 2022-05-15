package com.beomsu317.core_ui.navigation

import androidx.annotation.DrawableRes
import com.beomsu317.core.R

const val ROOT_GRAPH_ROUTE = "root"
const val STARTUP_GRAPH_ROUTE = "startup"
const val FRIENDS_GRAPH_ROUTE = "friends"
const val CHAT_GRAPH_ROUTE = "chat"
const val PROFILE_GRAPH_ROUTE = "profile"

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