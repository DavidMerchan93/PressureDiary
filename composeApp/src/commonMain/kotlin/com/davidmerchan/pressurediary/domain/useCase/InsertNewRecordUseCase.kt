package com.davidmerchan.pressurediary.domain.useCase

import com.davidmerchan.pressurediary.domain.model.PressureLogModel
import com.davidmerchan.pressurediary.domain.repository.PressureLogRepository

class InsertNewRecordUseCase(
    private val pressureLogRepository: PressureLogRepository
) {
    suspend operator fun invoke(
        date: Long,
        systolic: Double,
        diastolic: Double,
        activity: Int,
    ): Result<Boolean> {
        return pressureLogRepository.insertPressureLog(
            PressureLogModel(
                date = date,
                systolic = systolic,
                diastolic = diastolic,
                activity = activity
            )
        )
    }
}
