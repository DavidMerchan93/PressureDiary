package com.davidmerchan.pressurediary.domain.useCase

import com.davidmerchan.pressurediary.domain.model.PressureLogModel
import com.davidmerchan.pressurediary.domain.repository.PressureLogRepository

class GetAllRecordsUseCase(
    private val pressureLogRepository: PressureLogRepository
) {
    suspend operator fun invoke(): Result<List<PressureLogModel>> {
        return pressureLogRepository.getPressureLogs()
    }
}
