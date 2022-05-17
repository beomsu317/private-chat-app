package com.beomsu317.core_ui.components.button

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.TextButton
import androidx.compose.runtime.*

@Composable
fun DebounceTextButton(
    debounceInterval: Long = 300L,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    var lastClickTimeMs by remember { mutableStateOf(0L) }
    var now by remember { mutableStateOf(System.currentTimeMillis()) }
    TextButton(
        onClick = {
            if (now - lastClickTimeMs < debounceInterval) {
                return@TextButton
            }
            onClick()
            lastClickTimeMs = now
        },
        content = content
    )
}