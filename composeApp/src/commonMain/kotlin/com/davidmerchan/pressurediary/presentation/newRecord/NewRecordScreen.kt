package com.davidmerchan.pressurediary.presentation.newRecord

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import pressurediary.composeapp.generated.resources.Res
import pressurediary.composeapp.generated.resources.btn_save_record
import pressurediary.composeapp.generated.resources.title_field_date
import pressurediary.composeapp.generated.resources.title_press_diastolic
import pressurediary.composeapp.generated.resources.title_press_systolic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRecordScreen(modifier: Modifier = Modifier, onBackPressed: () -> Unit) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("Nuevo registro")
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(stringResource(Res.string.title_press_systolic))
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(stringResource(Res.string.title_press_diastolic))
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(stringResource(Res.string.title_field_date))
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {}
            ) {
                Text(stringResource(Res.string.btn_save_record))
            }
        }
    }
}
