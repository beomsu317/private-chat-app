package com.beomsu317.core_ui.components.button

import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun DebounceIconButton(
    debounceInterval: Long = 300L,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var lastClickTimeMs by remember { mutableStateOf(0L) }
    IconButton(
        onClick = {
            val now = System.currentTimeMillis()
            if (now - lastClickTimeMs < debounceInterval) {
                return@IconButton
            }
            onClick()
            lastClickTimeMs = now
        },
        modifier = modifier,
        content = content
    )
}