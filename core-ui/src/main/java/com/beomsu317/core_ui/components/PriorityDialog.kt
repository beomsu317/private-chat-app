package com.beomsu317.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun PriorityDialog(
    onClose: () -> Unit,
    onOkClick: (Int) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(2f) }
    Dialog(
        onDismissRequest = { onClose() },
    ) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
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

@Preview
@Composable
fun PriorityDialogPreview() {
    PriorityDialog(onClose = {}, onOkClick = {})
}