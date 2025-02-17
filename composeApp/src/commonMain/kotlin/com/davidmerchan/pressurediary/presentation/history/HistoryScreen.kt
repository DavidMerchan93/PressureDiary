package com.davidmerchan.pressurediary.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.davidmerchan.pressurediary.presentation.components.PressureDialog
import com.davidmerchan.pressurediary.presentation.components.PressureLogItem
import com.davidmerchan.pressurediary.presentation.theme.history.HistoryScreenEvents
import com.davidmerchan.pressurediary.presentation.theme.history.HistoryViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pressurediary.composeapp.generated.resources.Res
import pressurediary.composeapp.generated.resources.btn_ok_dialog
import pressurediary.composeapp.generated.resources.message_load_records
import pressurediary.composeapp.generated.resources.messages_general_error
import pressurediary.composeapp.generated.resources.title_error_save_log
import pressurediary.composeapp.generated.resources.title_general_error
import pressurediary.composeapp.generated.resources.title_history

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    val historyViewModel = koinViewModel<HistoryViewModel>()
    val historyState = historyViewModel.historyState.value

    LaunchedEffect(Unit) {
        historyViewModel.handleEvent(HistoryScreenEvents.LoadData)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.title_history))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPressed()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when {
                historyState.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Text(stringResource(Res.string.message_load_records))
                    }
                }

                historyState.error != null -> {
                    PressureDialog(
                        title = stringResource(Res.string.title_general_error),
                        detail = stringResource(Res.string.messages_general_error),
                        buttonOk = stringResource(Res.string.btn_ok_dialog),
                        onDismissRequest = {}
                    )
                }

                historyState.records?.isNotEmpty() == true -> {
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(historyState.records) { item ->
                            PressureLogItem(item)
                        }
                    }
                }
            }
        }
    }
}
