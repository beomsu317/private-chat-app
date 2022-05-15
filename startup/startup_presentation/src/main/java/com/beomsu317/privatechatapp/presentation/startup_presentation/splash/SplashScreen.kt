package com.beomsu317.privatechatapp.presentation.startup_presentation.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beomsu317.core.R
import com.beomsu317.core_ui.common.OneTimeEvent
import kotlinx.coroutines.flow.collect

@Composable
fun SplashScreen(
    onNavigateSignIn: () -> Unit,
    onNavigateFriendsList: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val oneTimeEventFlow = viewModel.oneTimeEventFlow

    LaunchedEffect(key1 = oneTimeEventFlow) {
        oneTimeEventFlow.collect { oneTimeEvent ->
            when (oneTimeEvent) {
                is OneTimeEvent.SignedIn -> {
                    onNavigateFriendsList()
                }
                is OneTimeEvent.NeedSignIn -> {
                    onNavigateSignIn()
                }
            }
        }
    }

    var scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
    }
    Box(
       modifier = Modifier
           .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
           modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(200.dp)
                    .scale(scale.value),
            )
        }

    }
}