package com.beomsu317.privatechatapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.beomsu317.core.common.startService
import com.beomsu317.core.domain.use_case.CoreUseCases
import com.beomsu317.privatechatapp.navigation.bottom_navigation.bottomNavBarRoutes
import com.beomsu317.privatechatapp.navigation.chat.ChatScreen
import com.beomsu317.privatechatapp.navigation.friends.FriendsScreen
import com.beomsu317.privatechatapp.navigation.startup.StartupScreen
import com.beomsu317.privatechatapp.presentation.SetupNavGraph
import com.beomsu317.privatechatapp.service.ChatService
import com.beomsu317.privatechatapp.ui.theme.PrivateChatAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var coreUseCases: CoreUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            Box(modifier = Modifier.systemBarsPadding()) {
                PrivateChatAppTheme {
                    val navController = rememberNavController()
                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()

                    LaunchedEffect(key1 = Unit) {
                        scope.launch {
                            val token = coreUseCases.getTokenFlowUseCase().first()
                            if (token.isNotEmpty()) {
                                startService(ChatService::class.java)
                            }
                        }
                    }

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
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var bottomBarState by rememberSaveable {
        mutableStateOf(false)
    }
    when (navBackStackEntry?.destination?.route?.split('/')?.get(0)) {
        StartupScreen.SplashScreen.route,
        StartupScreen.IntroScreen.route,
        StartupScreen.SignInScreen.route,
        StartupScreen.SignUpScreen.route -> {
            bottomBarState = false
        }
        ChatScreen.ChatRoomScreen.route -> {
            bottomBarState = false
        }
        else -> bottomBarState = true
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
