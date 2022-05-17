package com.beomsu317.core_ui.common

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

inline fun Modifier.debounceClickable(
    debounceInterval: Long = 300L,
    crossinline onClick: () -> Unit
): Modifier = composed {
    var lastClickTimeMs by remember { mutableStateOf(0L) }
    var now by remember { mutableStateOf(System.currentTimeMillis()) }
    Modifier.clickable {
        if (now - lastClickTimeMs < debounceInterval) {
            return@clickable
        }
        lastClickTimeMs = now
        onClick()
    }
}

inline fun Modifier.debounceClickable(
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    debounceInterval: Long = 300L,
    crossinline onClick: () -> Unit
): Modifier = composed {
    var lastEventTimeMs by remember { mutableStateOf(0L) }
    var now by remember { mutableStateOf(System.currentTimeMillis()) }
    Modifier.clickable(interactionSource = interactionSource, indication = indication) {
        if (now - lastEventTimeMs < debounceInterval) {
            return@clickable
        }
        lastEventTimeMs = now
        onClick()
    }
}