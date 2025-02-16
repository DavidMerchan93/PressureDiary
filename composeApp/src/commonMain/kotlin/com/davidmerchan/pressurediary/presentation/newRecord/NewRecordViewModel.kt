package com.davidmerchan.pressurediary.presentation.newRecord

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.pressurediary.domain.useCase.InsertNewRecordUseCase
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.round

class NewRecordViewModel(
    private val insertNewRecordUseCase: InsertNewRecordUseCase
) : ViewModel() {

    var newRecordState = mutableStateOf(NewRecordScreenState())
        private set

    fun handleEvents(event: NewRecordScreenEvents) {
        when (event) {
            is NewRecordScreenEvents.SavePressureRecord -> {
                saveRecord(
                    event.date,
                    event.systolic,
                    event.diastolic,
                    event.activity,
                )
            }
        }
    }

    private fun saveRecord(
        date: Long,
        systolic: Double,
        diastolic: Double,
        activity: Int,
    ) {
        viewModelScope.launch {
            val result = insertNewRecordUseCase(
                date = date,
                systolic = systolic.roundTo(2),
                diastolic = diastolic.roundTo(2),
                activity = activity,
            )
            when {
                result.isSuccess -> {
                    newRecordState.value = NewRecordScreenState(successSavedRecord = true)
                }

                result.isFailure -> {
                    newRecordState.value = NewRecordScreenState(failureSavedRecord = true)
                }
            }
        }
    }
}

fun Double.roundTo(decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    return round(this * factor) / factor
}
