package com.beomsu317.profile_presentation.my_profile

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.*
import coil.request.ImageRequest
import coil.size.Size
import com.beomsu317.core.R
import com.beomsu317.core.domain.model.User
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.core_ui.common.debounceClickable
import com.beomsu317.core_ui.components.PrivateChatTopAppBar
import com.beomsu317.core_ui.components.dialog.ListDialog
import com.beomsu317.core_ui.components.dialog.ListDialogEvent
import com.beomsu317.core_ui.theme.Crimson
import com.beomsu317.core_ui.theme.WhiteSmoke
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File

@Composable
fun MyProfileScreen(
    showSnackbar: (String, SnackbarDuration) -> Unit,
    onSignOut: () -> Unit,
    onNavigateSettings: () -> Unit,
    onClickFriends: () -> Unit,
    onClickRooms: () -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val oneTimeEventFlow = viewModel.oneTimeEventFlow
    var edit by remember { mutableStateOf(false) }
    var uri by remember { mutableStateOf<Uri?>(null) }

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
            PrivateChatTopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clip(CircleShape)
                            .clickable {
                                if (edit) {
                                    viewModel.onEvent(MyProfileEvent.UploadProfileImage(uri = uri))
                                }
                                edit = !edit
                            }
                    ) {
                        Text(
                            text = if (!edit) "Edit" else "Save",
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            ProfileSection(
                user = state.user,
                edit = edit
            ) {
                it?.let {
                    uri = it
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(modifier = Modifier.padding(horizontal = 40.dp))
            Spacer(modifier = Modifier.height(20.dp))
            FriendsAndRoomsSection(user = state.user, onClickFriends = onClickFriends, onClickRooms = onClickRooms)
            Spacer(modifier = Modifier.height(20.dp))
            SettingsSection(
                onSignOut = {
                    viewModel.onEvent(MyProfileEvent.SignOut)
                    showSnackbar("Successfully signed out", SnackbarDuration.Short)
                    onSignOut()
                },
                onNavigateSettings = onNavigateSettings
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileSection(
    user: User,
    edit: Boolean,
    onSaveImage: (Uri) -> Unit
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    var showDialog by remember { mutableStateOf(false) }
    val arcState = remember { Animatable(initialValue = -90f) }

    LaunchedEffect(key1 = true) {
        arcState.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1500,
                delayMillis = 0,
                easing = LinearOutSlowInEasing
            )
        )
    }

    var cameraUri by remember {
        mutableStateOf(getCameraUri(context))
    }

    val imageLoader by remember {
        mutableStateOf(ImageLoader.Builder(context).build())
    }

    var bitmap: Bitmap? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = user.photoUrl) {
        val imageRequest = ImageRequest.Builder(context)
            .data(
                if (user.photoUrl.isEmpty()) {
                    R.drawable.user_placeholder
                } else {
                    user.photoUrl
                }
            )
            .crossfade(true)
            .size(Size.ORIGINAL)
            .target(
                onSuccess = {
                    bitmap = it.toBitmapOrNull()
                }
            )
            .build()
        imageLoader.enqueue(imageRequest)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            if (it) {
                val imageRequest = ImageRequest.Builder(context)
                    .data(cameraUri)
                    .crossfade(true)
                    .size(Size.ORIGINAL)
                    .target(
                        onSuccess = {
                            bitmap = it.toBitmapOrNull()
                        }
                    )
                    .build()
                imageLoader.enqueue(imageRequest)
                onSaveImage(cameraUri)
            }
        })

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val imageRequest = ImageRequest.Builder(context)
                .data(uri)
                .crossfade(true)
                .size(Size.ORIGINAL)
                .target(
                    onSuccess = {
                        bitmap = it.toBitmapOrNull()
                    }
                )
                .build()
            imageLoader.enqueue(imageRequest)
            onSaveImage(uri)
        }
    }

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA,
        onPermissionResult = {
            cameraUri = getCameraUri(context)
            cameraLauncher.launch(cameraUri)
        })


    if (showDialog) {
        ListDialog(
            dialogEvents = arrayOf(
                ListDialogEvent(
                    text = "camera",
                    resId = R.drawable.ic_baseline_camera_alt_24,
                    onClick = {
                        showDialog = false
                        when (cameraPermissionState.status) {
                            is PermissionStatus.Granted -> {
                                cameraUri = getCameraUri(context)
                                cameraLauncher.launch(cameraUri)
                            }
                            is PermissionStatus.Denied -> {
                                cameraPermissionState.launchPermissionRequest()
                            }
                        }
                    }
                ),
                ListDialogEvent(
                    text = "gallery",
                    resId = R.drawable.ic_baseline_photo_24,
                    onClick = {
                        galleryLauncher.launch("image/*")
                        showDialog = false
                    }
                )
            ),
            onClose = {
                showDialog = false
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier,
        ) {
            AsyncImage(
                model = bitmap,
                contentDescription = "profile_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(130.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .clickable(interactionSource = interactionSource, indication = null) {
                        if (edit) {
                            showDialog = true
                        }
                    }
                    .aspectRatio(1f)
            )

            Canvas(modifier = Modifier.size(130.dp)) {
                drawArc(
                    color = Color(0xff4b5ad1),
                    startAngle = -90f,
                    sweepAngle = arcState.value,
                    useCenter = false,
                    style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            if (edit) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(WhiteSmoke)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_camera_alt_24),
                        contentDescription = "camera",
                        modifier = Modifier
                            .padding(6.dp)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colors.onBackground,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = user.displayName,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = user.email,
            color = Color.Gray,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun FriendsAndRoomsSection(
    user: User,
    onClickFriends: () -> Unit,
    onClickRooms: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountItem(text = "Friends", count = user.friends.size, onClick = onClickFriends)
        Spacer(modifier = Modifier.width(50.dp))
        CountItem(text = "Rooms", count = user.rooms.size, onClick = onClickRooms)
    }
}

@Composable
fun CountItem(
    text: String,
    count: Int,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .debounceClickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = count.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun SettingsSection(
    onSignOut: () -> Unit,
    onNavigateSettings: () -> Unit,
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
            SettingItem(text = "Settings", resId = R.drawable.ic_baseline_settings_24, onClick = {
                onNavigateSettings()
            })
            Spacer(modifier = Modifier.height(8.dp))
            Divider(modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(modifier = Modifier.height(8.dp))
            SettingItem(
                text = "Sign out",
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
            .debounceClickable {
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

fun getCameraUri(context: Context): Uri {
    val photoFile = File.createTempFile(
        "IMG_",
        ".jpg",
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    )
    val uri = FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        photoFile
    )
    return uri
}