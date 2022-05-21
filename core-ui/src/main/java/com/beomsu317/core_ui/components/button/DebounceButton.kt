package com.beomsu317.core_ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun DebounceButton(
    debounceInterval: Long = 300L,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    var lastClickTimeMs by remember { mutableStateOf(0L) }
    Button(
        onClick = {
            val now = System.currentTimeMillis()
            if (now - lastClickTimeMs < debounceInterval) {
                return@Button
            }
            onClick()
            lastClickTimeMs = now
        },
        modifier = modifier,
        contentPadding = contentPadding,
        shape = shape,
        content = content
    )
}