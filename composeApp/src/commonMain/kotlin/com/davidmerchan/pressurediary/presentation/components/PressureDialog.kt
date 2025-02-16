package com.davidmerchan.pressurediary.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.ktor.websocket.Frame

@Composable
fun PressureDialog(
    title: String? = null,
    detail: String? = null,
    showDialog: Boolean = true,
    buttonOk: String? = null,
    buttonCancel: String? = null,
    onOk: () -> Unit? = {},
    onCancel: () -> Unit? = {},
    onDismissRequest: () -> Unit
) {
    if (showDialog.not()) return

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                title?.let {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                detail?.let {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    buttonOk?.let {
                        TextButton(onClick = {
                            onDismissRequest()
                            onOk()
                        }) {
                            Text(buttonOk)
                        }
                    }
                    buttonCancel?.let {
                        TextButton(onClick = {
                            onDismissRequest()
                            onCancel()
                        }) {
                            Text(buttonCancel)
                        }
                    }
                }
            }
        }
    }
}
