package com.davidmerchan.pressurediary.data.repository

import com.davidmerchan.pressurediary.BaseTest
import com.davidmerchan.pressurediary.domain.model.PressureLogModel
import com.davidmerchan.pressurediary.domain.repository.PressureLogRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertTrue

class PressureLogDatasourceTest : BaseTest() {
    private lateinit var pressureLogDatasource: PressureLogRepository

    private fun start() {
        pressureLogDatasource = get()
    }

    @Test
    fun `GIVEN call getHomePressureLogs WHEN size is 3 THEN return successful list of PressureLogModel`() =
        runTest {
            start()

            val result = pressureLogDatasource.getHomePressureLogs(3)

            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()?.size == 3)
        }

    @Test
    fun `GIVEN call getAllPressureLogs WHEN is all data THEN return successful list of PressureLogModel`() =
        runTest {
            start()

            val result = pressureLogDatasource.getPressureLogs()

            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()?.size == 10)
        }

    @Test
    fun `GIVEN call getLastPressureLog WHEN is data THEN return successful PressureLogModel`() =
        runTest {
            start()

            val result = pressureLogDatasource.getLastPressureLog()

            assertTrue(result.isSuccess)
            assertTrue((result.getOrNull()?.id ?: 0) > 0)
            assertTrue((result.getOrNull()?.date ?: 0) > 0)
            assertTrue((result.getOrNull()?.systolic ?: .0) > .0)
            assertTrue((result.getOrNull()?.diastolic ?: .0) > .0)
            assertTrue((result.getOrNull()?.activity ?: 0) > 0)
        }

    @Test
    fun `GIVEN call insertPressureLog WHEN is data THEN return successful boolean`() = runTest {
        start()

        val pressureLog = PressureLogModel(
            id = 0,
            date = 0L,
            systolic = .0,
            diastolic = .0,
            activity = 0
        )

        val result = pressureLogDatasource.insertPressureLog(pressureLog)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == true)
    }

}
