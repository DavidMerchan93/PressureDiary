package com.davidmerchan.pressurediary.presentation.home

import com.davidmerchan.pressurediary.BaseTest
import com.davidmerchan.pressurediary.domain.model.CardiovascularRiskModel
import com.davidmerchan.pressurediary.domain.model.IMCClassification
import org.koin.core.component.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HomeViewModelTest : BaseTest() {

    private lateinit var homeViewModel: HomeViewModel

    private fun start() {
        homeViewModel = get()
    }

    @Test
    fun `GIVEN handleEvent WHEN LoadData is called THEN records are loaded`() {
        start()

        homeViewModel.handleEvent(HomeScreenEvents.LoadData)

        with(homeViewModel.homeState.value) {
            assertTrue(homeRecords.isNotEmpty())
            assertEquals(homeRecords.size, 3)
        }
    }

    @Test
    fun `GIVEN handleEvent WHEN GetIMC is called THEN user IMC are loaded`() {
        start()

        homeViewModel.handleEvent(HomeScreenEvents.GetIMC)

        with(homeViewModel.homeState.value) {
            assertTrue(homeRecords.isEmpty())
            assertTrue(imcResult != null)
            assertTrue((imcResult?.imc ?: .0) > .0)
            assertTrue(imcResult?.classification != IMCClassification.DEFAULT)
        }
    }

    @Test
    fun `GIVEN handleEvent WHEN GetCardiovascularRisk is called THEN user Cardiovascular Risk are loaded`() {
        start()

        homeViewModel.handleEvent(HomeScreenEvents.GetCardiovascularRisk)

        with(homeViewModel.homeState.value) {
            assertTrue(homeRecords.isEmpty())
            assertEquals(imcResult, null)

            assertTrue(cardiovascularRisk != null)
            assertTrue(cardiovascularRisk != CardiovascularRiskModel.NOT_APPLICABLE)
        }
    }

}
