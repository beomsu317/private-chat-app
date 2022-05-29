package com.beomsu317.core_ui.components.text_field

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
) {
//    var focusRequester by remember { mutableStateOf(FocusRequester()) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = "Send message...",
                    color = Color.Gray,
                    style = TextStyle.Default
                )
            }
            innerTextField()
        },
        keyboardActions = KeyboardActions(onSend = {
            onSend()
        }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
        modifier = modifier
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
            .padding(16.dp),
        )
}