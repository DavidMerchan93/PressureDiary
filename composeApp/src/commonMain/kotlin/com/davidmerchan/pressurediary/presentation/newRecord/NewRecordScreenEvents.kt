package com.davidmerchan.pressurediary.presentation.newRecord

sealed interface NewRecordScreenEvents {
    data class SavePressureRecord(
        val date: Long,
        val systolic: Double,
        val diastolic: Double,
        val activity: Int,
    ) : NewRecordScreenEvents
}
