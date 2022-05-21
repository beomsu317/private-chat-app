package com.beomsu317.core_ui.common

import android.util.Log
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import java.text.SimpleDateFormat

inline fun Modifier.debounceClickable(
    debounceInterval: Long = 300L,
    crossinline onClick: () -> Unit
): Modifier = composed {
    var lastClickTime by remember { mutableStateOf(0L) }
    Modifier.clickable {
        val now = System.currentTimeMillis()
        if (now - lastClickTime < debounceInterval) {
            return@clickable
        }
        lastClickTime = now
        onClick()
    }
}

inline fun Modifier.debounceClickable(
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    debounceInterval: Long = 300L,
    crossinline onClick: () -> Unit
): Modifier = composed {
    var lastEventTime by remember { mutableStateOf(0L) }
    Modifier.clickable(interactionSource = interactionSource, indication = indication) {
        val now = System.currentTimeMillis()
        if (now - lastEventTime < debounceInterval) {
            return@clickable
        }
        lastEventTime = now
        onClick()
    }
}