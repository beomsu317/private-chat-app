package com.beomsu317.friends_presentation.friends_list

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.beomsu317.core.R
import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.core_ui.components.PrivateChatTopAppBar
import com.beomsu317.core_ui.components.SearchTextField
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun FriendsListScreen(
    showSnackbar: (String, SnackbarDuration) -> Unit,
    onAddFriendButtonClick: () -> Unit,
    viewModel: FriendsListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val oneTimeEventFlow = viewModel.oneTimeEventFlow
    LaunchedEffect(key1 = oneTimeEventFlow) {
        oneTimeEventFlow.collect { oneTimeEvet ->
            when (oneTimeEvet) {
                is OneTimeEvent.ShowSnackbar -> {
                    showSnackbar(oneTimeEvet.message, SnackbarDuration.Short)
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(FriendsListEvent.RefreshFriends(refresh = true))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddFriendButtonClick()
                },
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "floating_action_button")
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                PrivateChatTopAppBar(
                    title = {
                        Text(text = "Friends")
                    }
                )
                SearchSection(onSearch = {
                    viewModel.onEvent(FriendsListEvent.Search(it))
                })
                Spacer(modifier = Modifier.height(30.dp))
                FriendsListSection(
                    friends = state.friends,
                    onDeleteFriend = { friend ->
                        viewModel.onEvent(FriendsListEvent.DeleteFriend(friend = friend))
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
}

@Composable
fun SearchSection(
    onSearch: (String) -> Unit
) {
    SearchTextField(onSearch)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendsListSection(
    friends: Set<Friend>,
    onDeleteFriend: (Friend) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(friends.toList()) {
            Box(
               modifier = Modifier.animateItemPlacement(
                   animationSpec = tween(durationMillis = 600)
               )
            ) {
                Divider(modifier = Modifier.padding(horizontal = 20.dp))
                FriendItem(
                    friend = it,
                    onDeleteFriend = onDeleteFriend
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FriendItem(
    friend: Friend,
    onDeleteFriend: (Friend) -> Unit
) {
    SwipeableActionsBox(
        endActions = listOf(
            SwipeAction(
                onSwipe = {
                    onDeleteFriend(friend)
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 20.dp, vertical = 10.dp)
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
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = friend.displayName
                )
                Text(
                    text = friend.email
                )
            }
        }
    }
}

@Preview
@Composable
fun SwipeableSample() {
    val archive = SwipeAction(
        icon = painterResource(R.drawable.ic_baseline_photo_24),
        background = Color.Green,
        onSwipe = { }
    )

    val snooze = SwipeAction(
        icon = painterResource(R.drawable.ic_baseline_camera_alt_24),
        background = Color.Yellow,
        isUndo = true,
        onSwipe = { },
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SwipeableActionsBox(
            startActions = listOf(archive),
            endActions = listOf(snooze)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .height(50.dp)
                    .background(Color.DarkGray)
            ) {

            }
        }
    }
}