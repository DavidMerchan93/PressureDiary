package com.davidmerchan.pressurediary.data.database

import app.cash.sqldelight.db.SqlDriver
import com.davidmerchan.pressurediary.database.PressureDiaryDatabase
import com.davidmerchan.pressurediary.database.PressureLog
import com.davidmerchan.pressurediary.domain.model.PressureLogModel

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

class LocalDatabase(
    private val databaseDriverFactory: DatabaseDriverFactory
): LocalDataSource {
    private val database = PressureDiaryDatabase(databaseDriverFactory.createDriver())
    private val query = database.pressureDiaryDatabaseQueries

    override suspend fun getHomeRecords(size: Int): List<PressureLog> {
        return query.selectAllPressureLogInfo()
            .executeAsList()
            .takeLast(size)
    }

    override suspend fun getAllRecords(): List<PressureLog> {
        return query.selectAllPressureLogInfo()
            .executeAsList()
    }

    override suspend fun insertPressureLog(pressureLogModel: PressureLogModel) {
        query.insertPressureLog(
            id = pressureLogModel.id,
            uid = 0,
            date = pressureLogModel.date,
            systolic = pressureLogModel.systolic.toString(),
            diastolic = pressureLogModel.diastolic.toString(),
            activity = pressureLogModel.activity.toLong()
        )
    }
}
