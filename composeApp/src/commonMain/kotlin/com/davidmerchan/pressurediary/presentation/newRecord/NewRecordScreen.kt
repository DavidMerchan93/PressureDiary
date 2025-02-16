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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.davidmerchan.pressurediary.presentation.components.PressureDialog
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerDialog
import network.chaintech.kmp_date_time_picker.ui.datetimepicker.WheelDateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.dateTimeToString
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
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
import pressurediary.composeapp.generated.resources.title_error_save_log
import pressurediary.composeapp.generated.resources.title_field_date
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
    var selectedDate = 0L

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
                    newRecordViewModel.handleEvents(
                        NewRecordScreenEvents.SavePressureRecord(
                            date = selectedDate,
                            systolic = sliderSystolic.toDouble(),
                            diastolic = sliderDiastolic.toDouble(),
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
