package com.beomsu317.friends_presentation.friend_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.beomsu317.core.R
import com.beomsu317.core_ui.components.DebounceButton
import com.beomsu317.friends_domain.model.FriendWithPriority
import kotlin.math.roundToInt

@Composable
fun FriendProfileDialog(
    friendWithPriority: FriendWithPriority,
    onClose: (Int) -> Unit,
    onOneOnOneChatClick: (Int) -> Unit
) {
    var priority by remember { mutableStateOf(friendWithPriority.priority) }
    Dialog(
        onDismissRequest = {
            onClose(priority)
        }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            FriendsInfoSection(friendWithPriority = friendWithPriority)
            Spacer(modifier = Modifier.height(10.dp))
            PrioritySection(
                friendWithPriority = friendWithPriority,
                onPriorityChange = {
                    priority = it
                }
            )
            OneOnOneChat(
                onOneOnOneChatClick = {
                    onOneOnOneChatClick(priority)
                }
            )
        }
    }
}

@Composable
fun FriendsInfoSection(friendWithPriority: FriendWithPriority) {
    FriendImageSection(
        friend = friendWithPriority
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = friendWithPriority.displayName,
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = friendWithPriority.email,
        style = MaterialTheme.typography.body2,
        fontWeight = FontWeight.Normal,
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("${friendWithPriority.numberOfFriends}")
            }
            append(" friends    ")
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("${friendWithPriority.numberOfRooms}")
            }
            append(" rooms")
        },
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun FriendImageSection(
    friend: FriendWithPriority
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(
                if (friend.photoUrl.isEmpty()) {
                    R.drawable.user_placeholder
                } else {
                    friend.photoUrl
                }
            )
            .crossfade(true)
            .build(),
        contentDescription = "friend_profile_image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .padding(4.dp)
            .clip(CircleShape)
    )
}

@Composable
fun PrioritySection(
    friendWithPriority: FriendWithPriority,
    onPriorityChange: (Int) -> Unit
) {
    var priority by remember { mutableStateOf(friendWithPriority.priority.toFloat()) }

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Priority",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
        Slider(
            value = priority,
            onValueChange = {
                priority = it
                onPriorityChange(it.roundToInt())
            },
            valueRange = 0f..4f,
            steps = 3,
            modifier = Modifier
        )
    }
}

@Composable
fun OneOnOneChat(
    onOneOnOneChatClick: () -> Unit
) {
    DebounceButton(
        onClick = {
            onOneOnOneChatClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text("1:1 Chat Start")
    }
}