package com.beomsu317.profile_presentation.settings

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beomsu317.core_ui.components.PrivateChatTopAppBar
import com.beomsu317.profile_domain.BuildConfig

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        PrivateChatTopAppBar(
            title = {
                Text("Settings")
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        onNavigateBack()
                    },
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "arrow_back")
                }
            }
        )
        Spacer(modifier = Modifier
            .background(Color.LightGray))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp)
        ) {
            Notifications(
                notificationState = state.settings.notifications,
                onNotificationsToggle = {
                    viewModel.onEvent(SettingsEvent.NotificationsToggle(it))
                }
            )
        }
        Spacer(modifier = Modifier
            .background(Color.LightGray))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp),
        ) {
            Version()
        }
    }
}

@Composable
fun Notifications(
    notificationState: Boolean,
    onNotificationsToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "notifications",
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Notifications",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal
            )
        }
        Switch(
            checked = notificationState,
            onCheckedChange = {
                onNotificationsToggle(it)
            }
        )
    }
}

@Composable
fun Version() {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "version",
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Version",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal
            )
        }
        Text(
            text = context.packageManager.getPackageInfo(context.packageName, 0).versionName,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(
        onNavigateBack = {},
        viewModel = hiltViewModel()
    )
}

