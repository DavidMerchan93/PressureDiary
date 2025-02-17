package com.davidmerchan.pressurediary.domain.useCase

import com.davidmerchan.pressurediary.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertTrue

class GetAllRecordsUseCaseTest : BaseTest() {

    private lateinit var getAllRecordsUseCase: GetAllRecordsUseCase

    private fun start() {
        getAllRecordsUseCase = get()
    }

    @Test
    fun `GIVEN getAllRecordsUseCase WHEN invoke THEN return success list of PressureLogModel`() =
        runTest {
            start()

            val result = every(getAllRecordsUseCase(), true)

            assertTrue(result.isSuccess)
            assertTrue((result.getOrNull()?.size ?: 0) == 10)
        }

    @Test
    fun `GIVEN getAllRecordsUseCase WHEN invoke THEN return error`() = runTest {
        start()

        try {
            every(getAllRecordsUseCase(), false)
        } catch (e: Exception) {
            assertTrue(e.message == "Error in process")
        }
    }

}
