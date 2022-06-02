package com.beomsu317.chat_presentation.chat_room_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.beomsu317.chat_domain.model.RecentMessage
import com.beomsu317.core.R
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.core_ui.common.debounceClickable
import com.beomsu317.core_ui.components.PrivateChatTopAppBar
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun ChatRoomListScreen(
    onMessageClick: (String) -> Unit,
    showSnackbar: (String, SnackbarDuration) -> Unit,
    viewModel: ChatRoomListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val oneTimeEventFlow = viewModel.oneTimeEventFlow

    LaunchedEffect(key1 = Unit) {
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
                    text = "Messages"
                )
            }
        )
        MessagesSection(
            recentMessages = state.recentMessage,
            onMessageClick = onMessageClick,
            onLeaveRoom = { roomId ->
                viewModel.onEvent(ChatRoomListEvent.LeaveRoom(roomId))
            }
        )
    }
}

@Composable
fun MessagesSection(
    recentMessages: List<RecentMessage>,
    onMessageClick: (String) -> Unit,
    onLeaveRoom: (String) -> Unit,
) {
    LazyColumn {
        items(recentMessages) { recentMessage ->
            LastMessageItem(
                recentMessage = recentMessage,
                onMessageClick = onMessageClick,
                onDeleteRoom = onLeaveRoom

            )
            Divider()
        }
    }
}

@Composable
fun LastMessageItem(
    recentMessage: RecentMessage,
    onDeleteRoom: (String) -> Unit,
    onMessageClick: (String) -> Unit
) {
    var messageElapsedTime by remember { mutableStateOf((System.currentTimeMillis() / 1000) - (recentMessage.message.timestamp / 1000)) }

    SwipeableActionsBox(
        modifier = Modifier
            .fillMaxWidth(),
        endActions = listOf(
            SwipeAction(
                onSwipe = {
                    onDeleteRoom(recentMessage.message.roomId)
                },
                icon = {
                    Icon(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                        contentDescription = "delete"
                    )
                },
                background = Color.Red,
            )
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .debounceClickable {
                    onMessageClick(recentMessage.friend.id)
                }
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (recentMessage.message.read) {
                                    MaterialTheme.colors.background
                                } else {
                                    MaterialTheme.colors.primary
                                }
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                if (recentMessage.friend.photoUrl.isNotEmpty()) {
                                    recentMessage.friend.photoUrl
                                } else {
                                    R.drawable.user_placeholder
                                }
                            )
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.user_placeholder),
                        contentScale = ContentScale.Crop,
                        contentDescription = recentMessage.friend.displayName,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = recentMessage.friend.displayName,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    text =
                    if (messageElapsedTime > 0 && messageElapsedTime <= 60) {
                        "${messageElapsedTime}sec ago"
                    } else if (messageElapsedTime > 60 && messageElapsedTime <= 60 * 60) {
                        "${messageElapsedTime / 60}min ago"
                    } else if (messageElapsedTime > 60 * 60 && messageElapsedTime <= 60 * 60 * 24) {
                        "${messageElapsedTime / (60 * 60)}hr ago"
                    } else if (messageElapsedTime > 60 * 60 * 24 && messageElapsedTime <= 60 * 60 * 24 * 365) {
                        "${messageElapsedTime / (60 * 60 * 24)}day ago"
                    } else {
                        "${messageElapsedTime / (60 * 60 * 24 * 365)}yr ago"
                    },
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = recentMessage.message.message,
                style = MaterialTheme.typography.body1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 20.dp),
            )
        }
    }
}