package com.davidmerchan.pressurediary

import com.davidmerchan.pressurediary.data.database.LocalDataSource
import com.davidmerchan.pressurediary.database.PressureLog
import com.davidmerchan.pressurediary.domain.model.PressureLogModel
import kotlin.random.Random

class FakeLocalDataSource : LocalDataSource {
    override suspend fun getHomeRecords(size: Int): List<PressureLog> {
        return (1..size).map {
            fakePressureLog()
        }
    }

    override suspend fun getAllRecords(): List<PressureLog> {
        return (1..10).map {
            fakePressureLog()
        }
    }

    override suspend fun getLastRecord(): PressureLog {
        return fakePressureLog()
    }

    override suspend fun insertPressureLog(pressureLogModel: PressureLogModel) {
        println("Insertando registro")
    }

    private fun fakePressureLog(): PressureLog {
        return PressureLog(
            id = Random.nextLong(),
            uid = Random.nextLong(),
            date = Random.nextLong(1609459200000L, 1640995199000L),
            systolic = Random.nextDouble(100.0, 140.0).toString(),
            diastolic = Random.nextDouble(60.0, 100.0).toString(),
            activity = Random.nextLong(1, 3)
        )
    }
}
