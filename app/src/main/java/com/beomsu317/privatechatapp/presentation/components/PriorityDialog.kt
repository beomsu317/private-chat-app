package com.beomsu317.privatechatapp.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun PriorityDialog(
    onClose: () -> Unit,
    onOkClick: (Int) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(0f) }
    Dialog(
        onDismissRequest = { onClose() },
    ) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.background)
                    .padding(10.dp),

                ) {
                Text(
                    text = "Select Priority",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )

                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                    },
                    valueRange = 0f..4f,
                    steps = 3,
                    colors = SliderDefaults.colors(thumbColor = MaterialTheme.colors.primary, activeTrackColor = MaterialTheme.colors.primary)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onOkClick(sliderPosition.toInt())
                    }) {
                        Text(
                            text = "Ok"
                        )
                    }
                }
            }
        }
    }
}