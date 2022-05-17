package com.beomsu317.core_ui.components.button

import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun DebounceFloatingActionButton(
    debounceInterval: Long = 300L,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var lastClickTimeMs by remember { mutableStateOf(0L) }
    var now by remember { mutableStateOf(System.currentTimeMillis()) }

    FloatingActionButton(
        onClick = {
            if (now - lastClickTimeMs < debounceInterval) {
                return@FloatingActionButton
            }
            onClick()
            lastClickTimeMs = now
        },
        modifier = modifier,
        content = content
    )
}