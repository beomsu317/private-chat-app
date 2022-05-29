package com.beomsu317.chat_presentation.chat_room

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.beomsu317.core.R
import com.beomsu317.core.domain.model.Message
import com.beomsu317.core.domain.model.User
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.core_ui.components.PrivateChatTopAppBar
import com.beomsu317.core_ui.components.button.DebounceIconButton
import com.beomsu317.core_ui.components.text_field.ChatTextField
import com.beomsu317.core_ui.theme.WhiteSmoke
import java.text.SimpleDateFormat

@Composable
fun ChatRoomScreen(
    onNavigateBack: () -> Unit,
    showSnackbar: (String, SnackbarDuration) -> Unit,
    viewModel: ChatRoomViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val oneTimeEventFlow = viewModel.oneTimeEventFlow
    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = oneTimeEventFlow) {
        oneTimeEventFlow.collect { oneTimeEvent ->
            when (oneTimeEvent) {
                is OneTimeEvent.ShowSnackbar -> {
                    showSnackbar(oneTimeEvent.message, SnackbarDuration.Short)
                }
            }
        }
    }

    LaunchedEffect(key1 = state.messages) {
        if (lazyListState.layoutInfo.totalItemsCount != 0) {
            lazyListState.scrollToItem(index = lazyListState.layoutInfo.totalItemsCount - 1)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
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
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize(),
            ) {
                val (messageSection, sendMessageSection) = createRefs()
                MessagesSection(
                    messages = state.messages,
                    user = state.user,
                    lazyListState = lazyListState,
                    modifier = Modifier.constrainAs(messageSection) {
                        top.linkTo(parent.top)
                        bottom.linkTo(sendMessageSection.top)
                        height = Dimension.fillToConstraints
                    }
                )
                SendMessageSection(
                    onSendMessage = { text ->
                        if (text.isNotBlank()) {
                            viewModel.onEvent(ChatRoomEvent.SendMessage(text = text))
                        }
                    },
                    modifier = Modifier.constrainAs(sendMessageSection) {
                        top.linkTo(messageSection.bottom)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun MessagesSection(
    messages: List<Message>,
    user: User,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState
    ) {
        items(messages) { message ->
            if (user.id == message.senderId) {
                UserMessageItemSection(
                    message = message
                )
            } else {
                FriendMessageItemSection(
                    message = message,
                )
            }
        }
    }
}

@Composable
fun FriendMessageItemSection(
    message: Message
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .padding(horizontal = 16.dp, vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(end = 50.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(message.photoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = message.displayName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message.displayName,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "${SimpleDateFormat("EEEE hh:mm").format(message.timestamp)}",
                        style = MaterialTheme.typography.body2,
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Card(
                    modifier = Modifier,
                    shape = RoundedCornerShape(
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp
                    ),
                    backgroundColor = WhiteSmoke
                ) {
                    Text(
                        text = message.message,
                        style = MaterialTheme.typography.body1,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun UserMessageItemSection(
    message: Message
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .width(IntrinsicSize.Max)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "You",
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onBackground,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "${SimpleDateFormat("EEEE hh:mm").format(message.timestamp)}",
                    style = MaterialTheme.typography.body2,
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp
                    ),
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = message.message,
                        style = MaterialTheme.typography.body1,
                        color = WhiteSmoke,
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SendMessageSection(
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    Row(
        modifier = modifier
            .padding(start = 20.dp, top = 6.dp, bottom = 6.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ChatTextField(
            value = text,
            onValueChange = {
                text = it
            },
            onSend = {
                onSendMessage(text)
                text = ""
            },
            modifier = Modifier.weight(0.85f)
        )
        Box(modifier = Modifier.weight(0.05f))
        IconButton(
            onClick = {
                onSendMessage(text)
                text = ""
            },
            modifier = Modifier
                .weight(0.1f)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.primary)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "send",
                tint = Color.White,
            )
        }
    }
}