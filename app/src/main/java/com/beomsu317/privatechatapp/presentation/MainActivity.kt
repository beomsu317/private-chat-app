@file:OptIn(ExperimentalAnimationApi::class)

package com.beomsu317.privatechatapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.beomsu317.core_ui.navigation.*
import com.beomsu317.privatechatapp.presentation.ui.theme.PrivateChatAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrivateChatAppTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }
                ) { innerPadding ->
                    SetupNavGraph(
                        navHostController = navController,
                        innerPadding = innerPadding,
                        showSnackbar = { message, duration ->
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = message,
                                    duration = duration
                                )
                            }
                        })
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var bottomBarState by rememberSaveable {
        mutableStateOf(false)
    }
    when (navBackStackEntry?.destination?.parent?.route) {
        FRIENDS_GRAPH_ROUTE, CHAT_GRAPH_ROUTE, PROFILE_GRAPH_ROUTE -> bottomBarState = true
        else -> bottomBarState = false
    }

    AnimatedVisibility(
        visible = bottomBarState,
    ) {
        BottomNavigation {
            val currentDestination = navBackStackEntry?.destination
            bottomNavBarRoutes.forEach { screen ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true

                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(
                                id = if (isSelected == true) {
                                    screen.selectedResId
                                } else {
                                    screen.unselectedResId
                                }
                            ),
                            contentDescription = screen.label,
                        )
                    },
                    selected = isSelected == true,
                    label = {
                        Text(
                            text = screen.label
                        )
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = Color.White,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(FriendsScreen.FriendsListScreen.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        }
    }
}
