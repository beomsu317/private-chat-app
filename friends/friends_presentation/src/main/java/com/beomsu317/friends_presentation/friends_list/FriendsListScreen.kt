package com.beomsu317.friends_presentation.friends_list

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.beomsu317.core.R
import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.core_ui.common.debounceClickable
import com.beomsu317.core_ui.components.PrivateChatTopAppBar
import com.beomsu317.core_ui.components.SearchTextField
import com.beomsu317.core_ui.components.button.DebounceFloatingActionButton
import com.beomsu317.friends_domain.model.FriendWithPriority
import com.beomsu317.friends_domain.model.toFriend
import com.beomsu317.friends_presentation.friend_profile.FriendProfileDialog
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun FriendsListScreen(
    showSnackbar: (String, SnackbarDuration) -> Unit,
    onAddFriendButtonClick: () -> Unit,
    onOneOnOneChatClick: (String) -> Unit,
    viewModel: FriendsListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val oneTimeEventFlow = viewModel.oneTimeEventFlow
    val scope = rememberCoroutineScope()

    var fabHeight by remember { mutableStateOf(0.dp) }
    val fabHeightState by animateDpAsState(
        targetValue = fabHeight,
        spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    var showProfileDialog by remember { mutableStateOf(false) }
    var currentFriend by remember { mutableStateOf(FriendWithPriority()) }
    var isNavigating by remember { mutableStateOf(false) }

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
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            DebounceFloatingActionButton(
                onClick = {
                    onAddFriendButtonClick()
                },
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = -16.dp.roundToPx(),
                            y = fabHeightState.roundToPx() - 16.dp.roundToPx()
                        )
                    }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "floating_action_button")
            }
        }
    ) { paddingValues ->
        if (showProfileDialog) {
            FriendProfileDialog(
                friendWithPriority = currentFriend,
                onClose = { priority ->
                    if (currentFriend.priority != priority) {
                        viewModel.onEvent(FriendsListEvent.UpdateUser(currentFriend.copy(priority = priority)))
                    }
                    showProfileDialog = false
                },
                onOneOnOneChatClick = { priority ->
                    if (currentFriend.priority != priority) {
                        viewModel.onEvent(FriendsListEvent.UpdateUser(currentFriend.copy(priority = priority)))
                    }
                    onOneOnOneChatClick(currentFriend.id)
                    showProfileDialog = false
                    isNavigating = true
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PrivateChatTopAppBar(
                title = {
                    Text(text = "Friends")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchTextField(onSearch = {
                viewModel.searchText = it
                viewModel.onEvent(FriendsListEvent.Search)
            })
            Spacer(modifier = Modifier.height(10.dp))
            FriendsListSection(
                friends = state.friends,
                onDeleteFriend = { friend ->
                    viewModel.onEvent(FriendsListEvent.DeleteFriend(friendId = friend.id))
                },
                isLoading = state.isLoading,
                onRefresh = {
                    viewModel.onEvent(FriendsListEvent.RefreshFriends(refresh = true))
                },
                onScrollStateChange = { scrollState ->
                    if (scrollState) {
                        fabHeight = 100.dp
                    } else {
                        fabHeight = 0.dp
                    }
                },
                onShowProfileDialog = {
                    if (!isNavigating) {
                        currentFriend = it
                        showProfileDialog = true
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendsListSection(
    friends: List<FriendWithPriority>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onDeleteFriend: (FriendWithPriority) -> Unit,
    onScrollStateChange: (Boolean) -> Unit,
    onShowProfileDialog: (FriendWithPriority) -> Unit
) {
    val lazyListState = rememberLazyListState()

    val isScrollEnd by remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val firstVisibleItem = visibleItemsInfo.first()
                val lastVisibleItem = visibleItemsInfo.last()

                (firstVisibleItem.index != 0 &&
                        firstVisibleItem.offset != 0 &&
                        lastVisibleItem.index == layoutInfo.totalItemsCount - 1)
            }
        }
    }

    LaunchedEffect(key1 = isScrollEnd) {
        onScrollStateChange(isScrollEnd)
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = {
            onRefresh()
        }
    ) {
        LazyColumn(
            state = lazyListState,
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
                        onDeleteFriend = onDeleteFriend,
                        onShowProfileDialog = onShowProfileDialog
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FriendItem(
    friend: FriendWithPriority,
    onDeleteFriend: (FriendWithPriority) -> Unit,
    onShowProfileDialog: (FriendWithPriority) -> Unit
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
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .debounceClickable {
                    onShowProfileDialog(friend)
                }
                .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
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
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(2.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        text = friend.displayName,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = friend.email,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                }
            }
            PriorityIcon(
                priority = friend.priority
            )
        }
    }
}

@Composable
fun PriorityIcon(
    priority: Int
) {

    val arcState = remember { Animatable(initialValue = 90f) }

    LaunchedEffect(key1 = priority) {
        arcState.animateTo(
            targetValue = 360f / 5f * (priority + 1).toFloat(),
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            )
        )
    }
    Box(
        modifier = Modifier
            .size(36.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = Color(0xff4b5ad1),
                startAngle = -90f,
                sweepAngle = arcState.value,
                useCenter = false,
                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Text(
            text = (priority + 1).toString(),
            fontWeight = FontWeight.SemiBold
        )
    }
}