package com.davidmerchan.pressurediary.data.repository

import com.davidmerchan.pressurediary.data.database.LocalDataSource
import com.davidmerchan.pressurediary.domain.model.PressureLogModel
import com.davidmerchan.pressurediary.domain.repository.PressureLogRepository

class PressureLogDatasource(
    private val localDataSource: LocalDataSource
) : PressureLogRepository {
    override suspend fun getHomePressureLogs(size: Int): Result<List<PressureLogModel>> {
        return try {
            val data = localDataSource.getHomeRecords(size).map {
                PressureLogModel(
                    id = it.id,
                    date = it.date,
                    systolic = it.systolic.toDouble(),
                    diastolic = it.diastolic.toDouble(),
                    activity = it.activity.toInt()
                )
            }
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPressureLogs(): Result<List<PressureLogModel>> {
        return try {
            val data = localDataSource.getAllRecords().map {
                PressureLogModel(
                    id = it.id,
                    date = it.date,
                    systolic = it.systolic.toDouble(),
                    diastolic = it.diastolic.toDouble(),
                    activity = it.activity.toInt()
                )
            }
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertPressureLog(pressureLog: PressureLogModel): Result<Boolean> {
        return try {
            localDataSource.insertPressureLog(pressureLog)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}