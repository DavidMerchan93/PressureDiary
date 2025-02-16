package com.davidmerchan.pressurediary.domain.repository

import com.davidmerchan.pressurediary.domain.model.PressureLogModel

interface PressureLogRepository {
    suspend fun getHomePressureLogs(): Result<List<PressureLogModel>>
    suspend fun getPressureLogs(): Result<List<PressureLogModel>>
    suspend fun insertPressureLog(pressureLog: PressureLogModel): Result<Boolean>
}
