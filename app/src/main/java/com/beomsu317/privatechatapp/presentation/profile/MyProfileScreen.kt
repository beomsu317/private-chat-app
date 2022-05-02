package com.beomsu317.privatechatapp.presentation.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beomsu317.privatechatapp.R
import com.beomsu317.privatechatapp.domain.model.User
import com.beomsu317.privatechatapp.presentation.common.OneTimeEvent
import com.beomsu317.privatechatapp.presentation.components.TopAppBar
import com.beomsu317.privatechatapp.presentation.ui.theme.Crimson
import com.beomsu317.privatechatapp.presentation.ui.theme.WhiteSmoke

@Composable
fun MyProfileScreen(
    showSnackbar: (String, SnackbarDuration) -> Unit,
    onSignOut: () -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val oneTimeEventFlow = viewModel.oneTimeEventFlow

    LaunchedEffect(key1 = oneTimeEventFlow) {
        oneTimeEventFlow.collect { oneTimeEvent ->
            when (oneTimeEvent) {
                is OneTimeEvent.ShowSnackbar -> {
                    showSnackbar(oneTimeEvent.message, SnackbarDuration.Short)
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            ImageSection(user = state.user)
            Spacer(modifier = Modifier.height(20.dp))
            Divider(modifier = Modifier.padding(horizontal = 40.dp))
            Spacer(modifier = Modifier.height(20.dp))
            FriendsAndRoomsSection(user = state.user)
            Spacer(modifier = Modifier.height(20.dp))
            SettingsSection(
                onSignOut = {
                    viewModel.onEvent(MyProfileEvent.SignOut)
                    onSignOut()
                }
            )
        }
    }

    if (state.isLoading) {
        CircularProgressIndicator()
    }
}

@Composable
fun ImageSection(
    user: User
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_placeholder),
            contentDescription = "image"
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = user.displayName
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = user.email
        )
    }
}

@Composable
fun FriendsAndRoomsSection(
    user: User
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountItem(text = "Friends", count = user.friends.size)
        Spacer(modifier = Modifier.width(100.dp))
        CountItem(text = "Rooms", count = user.rooms.size)
    }
}

@Composable
fun CountItem(
    text: String,
    count: Int
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = count.toString(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SettingsSection(
    onSignOut: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SettingItem(text = "Settings", resId = R.drawable.ic_baseline_settings_24, onClick = {})
            SettingItem(text = "Information", resId = R.drawable.ic_baseline_info_24, onClick = {})
            Spacer(modifier = Modifier.height(8.dp))
            Divider(modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(modifier = Modifier.height(8.dp))
            SettingItem(
                text = "Log out",
                color = Crimson,
                resId = R.drawable.ic_baseline_logout_24,
                onClick = {
                    onSignOut()
                }
            )
        }
    }
}

@Composable
fun SettingItem(
    text: String,
    color: Color = MaterialTheme.colors.onBackground,
    @DrawableRes resId: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = resId),
                contentDescription = text,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(WhiteSmoke)
                    .padding(10.dp),
                tint = color
            )
            Spacer(modifier = Modifier.width(25.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
                color = color
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "arrow right",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 20.dp)
        )
    }
}