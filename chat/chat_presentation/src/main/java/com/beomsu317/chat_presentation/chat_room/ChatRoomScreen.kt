package com.beomsu317.chat_presentation.chat_room

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.beomsu317.core.R
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.core_ui.components.PrivateChatTopAppBar
import com.beomsu317.core_ui.components.button.DebounceIconButton
import kotlinx.coroutines.flow.collect

@Composable
fun ChatRoomScreen(
    onNavigateBack: () -> Unit,
    showSnackbar: (String, SnackbarDuration) -> Unit,
    viewModel: ChatRoomViewModel = hiltViewModel()
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PrivateChatTopAppBar(
            title = {
                Text(
                    text = state.friend.displayName
                )
            },
            navigationIcon = {
                DebounceIconButton(
                    onClick = {
                        onNavigateBack()
                    },
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "arrow_back")
                }
            },
            actions = {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            if (state.friend.photoUrl.isEmpty()) {
                                R.drawable.user_placeholder
                            } else {
                                state.friend.photoUrl
                            }
                        )
                        .build(),
                    contentDescription = "friend_profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(end = 14.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                )
            }
        )
    }
}