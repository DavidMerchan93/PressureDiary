package com.davidmerchan.pressurediary.presentation.newRecord

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidmerchan.pressurediary.presentation.components.HorizontalButtonItem
import com.davidmerchan.pressurediary.presentation.components.HorizontalButtons
import com.davidmerchan.pressurediary.presentation.components.PressureDialog
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import network.chaintech.kmp_date_time_picker.ui.datetimepicker.WheelDateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.dateTimeToString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pressurediary.composeapp.generated.resources.Res
import pressurediary.composeapp.generated.resources.btn_ok_dialog
import pressurediary.composeapp.generated.resources.btn_save_record
import pressurediary.composeapp.generated.resources.btn_select_date
import pressurediary.composeapp.generated.resources.rest
import pressurediary.composeapp.generated.resources.run
import pressurediary.composeapp.generated.resources.run_fast
import pressurediary.composeapp.generated.resources.title_empty_activity
import pressurediary.composeapp.generated.resources.title_empty_date
import pressurediary.composeapp.generated.resources.title_error_save_log
import pressurediary.composeapp.generated.resources.title_new_record
import pressurediary.composeapp.generated.resources.title_press_diastolic
import pressurediary.composeapp.generated.resources.title_press_systolic
import pressurediary.composeapp.generated.resources.title_select_date
import pressurediary.composeapp.generated.resources.title_status_rest
import pressurediary.composeapp.generated.resources.title_status_run
import pressurediary.composeapp.generated.resources.title_status_run_fast
import pressurediary.composeapp.generated.resources.title_success_save_log
import pressurediary.composeapp.generated.resources.title_user_state

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
    onBackPressed: () -> Unit
) {

    val newRecordViewModel = koinViewModel<NewRecordViewModel>()
    val newRecordState = newRecordViewModel.newRecordState.value

    var sliderSystolic by remember { mutableStateOf(120f) }
    var sliderDiastolic by remember { mutableStateOf(80f) }

    var selectState by remember { mutableStateOf(UserState.DEFAULT) }
    var selectedDate by remember { mutableStateOf(0L) }

    var dateNotFound by remember { mutableStateOf(false) }
    var activityNotFound by remember { mutableStateOf(false) }

    if (dateNotFound) {
        ErrorFormDialog(
            showDialog = dateNotFound,
            message = stringResource(Res.string.title_empty_date),
            onDismissRequest = {
                dateNotFound = false
            }
        )
    }

    if (activityNotFound) {
        ErrorFormDialog(
            showDialog = activityNotFound,
            message = stringResource(Res.string.title_empty_activity),
            onDismissRequest = {
                activityNotFound = false
            }
        )
    }

    when {
        newRecordState.successSavedRecord -> {
            PressureDialog(
                onDismissRequest = {},
                detail = stringResource(Res.string.title_success_save_log),
                buttonOk = stringResource(Res.string.btn_ok_dialog),
                onOk = {
                    onBackPressed()
                }
            )
        }

        newRecordState.failureSavedRecord -> {
            PressureDialog(
                onDismissRequest = {},
                detail = stringResource(Res.string.title_error_save_log),
                buttonOk = stringResource(Res.string.btn_ok_dialog)
            )
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.title_new_record))
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
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            PressureSlider(
                pressure = sliderSystolic,
                label = stringResource(Res.string.title_press_systolic),
                minPressure = 90f,
                maxPressure = 200f,
                onValueChange = {
                    sliderSystolic = it
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            PressureSlider(
                pressure = sliderDiastolic,
                label = stringResource(Res.string.title_press_diastolic),
                minPressure = 70f,
                maxPressure = 120f,
                onValueChange = {
                    sliderDiastolic = it
                }
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
            HorizontalButtons(
                buttons = listOf(
                    HorizontalButtonItem(
                        image = Res.drawable.rest,
                        title = stringResource(Res.string.title_status_rest),
                        state = UserState.REST
                    ),
                    HorizontalButtonItem(
                        image = Res.drawable.run,
                        title = stringResource(Res.string.title_status_run),
                        state = UserState.RUN
                    ),
                    HorizontalButtonItem(
                        image = Res.drawable.run_fast,
                        title = stringResource(Res.string.title_status_run_fast),
                        state = UserState.RUN_FAST
                    ),
                ),
                selectState = selectState,
                onSelectedState = {
                    selectState = it
                }
            )
            Button(
                onClick = {
                    when {
                        selectedDate == 0L -> dateNotFound = true
                        selectState == UserState.DEFAULT -> activityNotFound = true
                        else -> {
                            newRecordViewModel.handleEvents(
                                NewRecordScreenEvents.SavePressureRecord(
                                    date = selectedDate,
                                    systolic = sliderSystolic.toDouble(),
                                    diastolic = sliderDiastolic.toDouble(),
                                    activity = selectState.id
                                )
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(Res.string.btn_save_record))
            }
        }
    }
}

@Composable
fun ErrorFormDialog(
    showDialog: Boolean,
    message: String,
    onDismissRequest: () -> Unit
) {
    PressureDialog(
        onDismissRequest = onDismissRequest,
        showDialog = showDialog,
        detail = message,
        buttonOk = stringResource(Res.string.btn_ok_dialog)
    )
}

@Composable
fun PressureSlider(
    pressure: Float,
    label: String,
    minPressure: Float,
    maxPressure: Float,
    onValueChange: (Float) -> Unit
) {
    Text(
        text = label
    )
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = pressure.toDouble().roundTo(2).toString(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
    Slider(
        valueRange = minPressure..maxPressure,
        value = pressure,
        onValueChange = onValueChange
    )
}

@Composable
fun DatePickerSelector(
    onDateSelected: (Long) -> Unit
) {

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedDate.isEmpty()) {
            Text(stringResource(Res.string.title_select_date))
        } else {
            Text(selectedDate)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDatePicker = true }) {
            Text(stringResource(Res.string.btn_select_date))
        }
    }

    if (showDatePicker) {
        WheelDateTimePickerView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp, bottom = 26.dp),
            title = stringResource(Res.string.title_select_date),
            doneLabel = stringResource(Res.string.btn_ok_dialog),
            showDatePicker = showDatePicker,
            rowCount = 5,
            height = 170.dp,
            shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
            onDoneClick = {
                selectedDate = dateTimeToString(it, "yyyy-MM-dd hh:mm a")
                onDateSelected(localDateTimeToMillis(it))
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            },
        )
    }
}

private fun localDateTimeToMillis(localDateTime: LocalDateTime): Long {
    val instant: Instant = localDateTime.toInstant(TimeZone.currentSystemDefault())
    return instant.toEpochMilliseconds()

}
