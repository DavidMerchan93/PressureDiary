package com.davidmerchan.pressurediary.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidmerchan.pressurediary.domain.model.PressureLogModel
import com.davidmerchan.pressurediary.presentation.components.PressureLogItem
import org.jetbrains.compose.resources.stringResource
import pressurediary.composeapp.generated.resources.Res
import pressurediary.composeapp.generated.resources.btn_show_all
import pressurediary.composeapp.generated.resources.title_logs
import pressurediary.composeapp.generated.resources.title_no_records

@Composable
fun LastRecords(
    modifier: Modifier = Modifier,
    pressureLogs: List<PressureLogModel>,
    onGotToHistory: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp),
                    text = stringResource(Res.string.title_logs),
                    fontSize = 20.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
                TextButton(
                    onClick = onGotToHistory
                ) {
                    Row {
                        Text(stringResource(Res.string.btn_show_all))
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                    }
                }
            }
            if (pressureLogs.isNotEmpty()) {
                Column {
                    pressureLogs.forEach {
                        PressureLogItem(it)
                    }
                }
            } else {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    text = stringResource(Res.string.title_no_records),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}