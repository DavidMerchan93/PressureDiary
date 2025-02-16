package com.davidmerchan.pressurediary.domain.useCase

import com.davidmerchan.pressurediary.domain.model.PressureLogModel
import com.davidmerchan.pressurediary.domain.repository.PressureLogRepository

class InsertNewRecordUseCase(
    private val pressureLogRepository: PressureLogRepository
) {
    suspend operator fun invoke(pressureLog: PressureLogModel): Result<Boolean> {
        return pressureLogRepository.insertPressureLog(pressureLog)
    }
}
