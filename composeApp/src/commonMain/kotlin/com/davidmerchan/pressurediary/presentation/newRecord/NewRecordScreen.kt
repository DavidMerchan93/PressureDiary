package com.davidmerchan.pressurediary.presentation.newRecord

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.davidmerchan.pressurediary.domain.model.PressureLogModel
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pressurediary.composeapp.generated.resources.Res
import pressurediary.composeapp.generated.resources.btn_cancel_dialog
import pressurediary.composeapp.generated.resources.btn_minus_date
import pressurediary.composeapp.generated.resources.btn_ok_dialog
import pressurediary.composeapp.generated.resources.btn_plus_date
import pressurediary.composeapp.generated.resources.btn_save_record
import pressurediary.composeapp.generated.resources.btn_select_date
import pressurediary.composeapp.generated.resources.rest
import pressurediary.composeapp.generated.resources.run
import pressurediary.composeapp.generated.resources.run_fast
import pressurediary.composeapp.generated.resources.title_field_date
import pressurediary.composeapp.generated.resources.title_new_record
import pressurediary.composeapp.generated.resources.title_press_diastolic
import pressurediary.composeapp.generated.resources.title_press_systolic
import pressurediary.composeapp.generated.resources.title_select_date
import pressurediary.composeapp.generated.resources.title_status_rest
import pressurediary.composeapp.generated.resources.title_status_run
import pressurediary.composeapp.generated.resources.title_status_run_fast
import pressurediary.composeapp.generated.resources.title_user_state
import kotlin.random.Random

enum class UserState(
    val id: Int,
    val state: String
) {
    REST(1, "En reposo"),
    RUN(2, "Actividad fisica leve"),
    RUN_FAST(3, "Actividad fisica intensa"),
    DEFAULT(4, "Default")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRecordScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onSaveRecord: (PressureLogModel) -> Unit
) {
    var pressSystolic by remember { mutableStateOf("0.0") }
    var pressDistolic by remember { mutableStateOf("0.0") }
    var selectState by remember { mutableStateOf(UserState.DEFAULT) }
    var selectedDate = 0L

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.title_new_record))
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
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(
                value = pressSystolic,
                onValueChange = {
                    pressSystolic = it
                },
                label = {
                    Text(stringResource(Res.string.title_press_systolic))
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = pressDistolic,
                onValueChange = {
                    pressDistolic = it
                },
                label = {
                    Text(stringResource(Res.string.title_press_diastolic))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            DatePickerSelector(
                onDateSelected = {
                    selectedDate = it
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(Res.string.title_user_state)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActivityButton(
                    image = Res.drawable.rest,
                    title = stringResource(Res.string.title_status_rest),
                    state = UserState.REST,
                    isSelected = selectState == UserState.REST,
                    onSelectState = {
                        selectState = it
                    }
                )
                ActivityButton(
                    image = Res.drawable.run,
                    title = stringResource(Res.string.title_status_run),
                    state = UserState.RUN,
                    isSelected = selectState == UserState.RUN,
                    onSelectState = {
                        selectState = it
                    }
                )
                ActivityButton(
                    image = Res.drawable.run_fast,
                    title = stringResource(Res.string.title_status_run_fast),
                    state = UserState.RUN_FAST,
                    isSelected = selectState == UserState.RUN_FAST,
                    onSelectState = {
                        selectState = it
                    }
                )
            }
            Button(
                onClick = {
                    onSaveRecord(
                        PressureLogModel(
                            id = Random.nextLong(),
                            date = selectedDate,
                            systolic = pressSystolic.toDouble(),
                            diastolic = pressDistolic.toDouble(),
                            activity = selectState.id
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(Res.string.btn_save_record))
            }
        }
    }
}

@Composable
fun DatePickerSelector(
    onDateSelected: (Long) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.parse("2025-02-15")) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(
                Res.string.title_field_date,
                "${selectedDate.dayOfMonth}/${selectedDate.monthNumber}/${selectedDate.year}"
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDatePicker = true }) {
            Text(stringResource(Res.string.btn_select_date))
        }
    }

    if (showDatePicker) {
        DatePickerOverlay(
            initialDate = selectedDate,
            onDateSelected = { newDate ->
                selectedDate = newDate
                showDatePicker = false
                onDateSelected(newDate.toEpochDays().toLong())
            },
            onDismissRequest = { showDatePicker = false }
        )
    }
}

@Composable
fun RowScope.ActivityButton(
    image: DrawableResource,
    title: String,
    state: UserState,
    isSelected: Boolean = false,
    onSelectState: (UserState) -> Unit = {}
) {
    Button(
        onClick = {
            onSelectState(state)
        },
        modifier = Modifier
            .padding(8.dp)
            .weight(1f)
            .aspectRatio(1f)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    )
                } else Modifier
            ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                painter = painterResource(image),
                contentDescription = null
            )
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DatePickerOverlay(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit
) {
    var selectedDate by remember { mutableStateOf(initialDate) }
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    stringResource(Res.string.title_select_date),
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Show current date
                Text(
                    text = stringResource(
                        Res.string.title_field_date,
                        "${selectedDate.dayOfMonth}/${selectedDate.monthNumber}/${selectedDate.year}"
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        selectedDate = selectedDate.minus(DatePeriod(days = 1))
                    }) {
                        Text(stringResource(Res.string.btn_minus_date))
                    }
                    Button(onClick = {
                        selectedDate = selectedDate.plus(DatePeriod(days = 1))
                    }) {
                        Text(stringResource(Res.string.btn_plus_date))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(Res.string.btn_cancel_dialog))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { onDateSelected(selectedDate) }) {
                        Text(stringResource(Res.string.btn_ok_dialog))
                    }
                }
            }
        }
    }
}

