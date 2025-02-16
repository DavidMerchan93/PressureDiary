package com.davidmerchan.pressurediary.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidmerchan.pressurediary.domain.model.PressureLogModel
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pressurediary.composeapp.generated.resources.Res
import pressurediary.composeapp.generated.resources.btn_show_all
import pressurediary.composeapp.generated.resources.title_home
import pressurediary.composeapp.generated.resources.title_logs
import pressurediary.composeapp.generated.resources.title_no_records
import pressurediary.composeapp.generated.resources.title_press_item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAddNewRecord: () -> Unit,
    onGoToSettings: () -> Unit
) {
    val homeViewModel = koinViewModel<HomeViewModel>()
    val homeState = homeViewModel.homeState.value

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.title_home))
                },
                actions = {
                    TextButton(
                        onClick = {
                            onGoToSettings()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddNewRecord()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp),
        ) {
            Column {
                LastRecords(pressureLogs = homeState.homeRecords)
            }
        }
    }
}

@Composable
fun LastRecords(modifier: Modifier = Modifier, pressureLogs: List<PressureLogModel>) {
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
                    onClick = {}
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

@Composable
fun PressureLogItem(pressure: PressureLogModel) {
    Column {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = formatTimestamp(pressure.date),
                fontSize = 18.sp
            )
            Text(
                text = stringResource(Res.string.title_press_item),
                fontSize = 12.sp,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${pressure.systolic} / ${pressure.diastolic}",
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
        }
        HorizontalDivider()
    }
}

fun formatTimestamp(timestamp: Long): String {
    val dateTime = Instant.fromEpochMilliseconds(timestamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())

    return "${dateTime.dayOfMonth}, ${dateTime.month} de ${dateTime.year}"
}