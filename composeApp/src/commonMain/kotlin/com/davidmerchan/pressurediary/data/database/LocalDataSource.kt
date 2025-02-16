package com.davidmerchan.pressurediary.data.database

import com.davidmerchan.pressurediary.database.PressureLog
import com.davidmerchan.pressurediary.domain.model.PressureLogModel

interface LocalDataSource {
    suspend fun getHomeRecords(size: Int): List<PressureLog>
    suspend fun getAllRecords(): List<PressureLog>
    suspend fun insertPressureLog(pressureLogModel: PressureLogModel)
}