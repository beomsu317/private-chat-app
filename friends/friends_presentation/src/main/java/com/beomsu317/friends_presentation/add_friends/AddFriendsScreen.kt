package com.beomsu317.friends_presentation.add_friends

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.beomsu317.core_ui.components.PriorityDialog
import com.beomsu317.core.R
import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core_ui.components.DebounceButton
import com.beomsu317.core_ui.components.PrivateChatTopAppBar
import com.beomsu317.core_ui.components.button.DebounceIconButton

@Composable
fun AddFriendsScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddFriendsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var friendId by rememberSaveable { mutableStateOf("") }

    if (showDialog) {
        PriorityDialog(
            onClose = {
                showDialog = false
            },
            onConfirmClick = { priority ->
                viewModel.onEvent(
                    AddFriendsEvent.AddFriend(
                        friendId = friendId,
                        priority = priority
                    )
                )
                showDialog = false
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PrivateChatTopAppBar(
                title = {
                    Text(text = "Add Friends")
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Center
                    ) {
                        DebounceIconButton(
                            onClick = {
                                onNavigateBack()
                            },
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "arrow_back"
                            )
                        }
                    }
                }
            )
            FriendsListSection(
                friends = state.friends,
                onFriendAddClick = {
                    friendId = it
                    showDialog = true
                }
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun FriendsListSection(
    friends: Set<Friend>,
    onFriendAddClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(friends.toList()) {
            FriendItem(friend = it, onFriendAddClick = onFriendAddClick)
        }
    }
}

@Composable
fun FriendItem(
    friend: Friend,
    onFriendAddClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            if (!friend.photoUrl.isEmpty()) {
                                friend.photoUrl
                            } else {
                                R.drawable.user_placeholder
                            }
                        )
                        .size(Size.ORIGINAL)
                        .crossfade(true)
                        .build(),
                    contentDescription = friend.displayName,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.primary,
                            shape = CircleShape
                        )
                        .padding(2.dp)
                )
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = friend.displayName,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = friend.email,
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray
                    )
                }

                Button(
                    onClick = {
                        onFriendAddClick(friend.id)
                    },
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Add"
                    )
                }
            }
        }
    }
}