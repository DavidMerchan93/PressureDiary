package com.davidmerchan.pressurediary.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.pressurediary.domain.useCase.GetHealthCareTipUseCase
import com.davidmerchan.pressurediary.domain.useCase.GetHomeRecordsUseCase
import com.davidmerchan.pressurediary.domain.useCase.GetIMCUseCase
import com.davidmerchan.pressurediary.domain.useCase.HasCardiovascularRiskUserCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeRecordsUseCase: GetHomeRecordsUseCase,
    private val getIMCUseCase: GetIMCUseCase,
    private val hasCardiovascularRiskUserCase: HasCardiovascularRiskUserCase,
    private val getHealthCareTipUseCase: GetHealthCareTipUseCase
) : ViewModel() {

    var homeState = mutableStateOf(HomeScreenState())
        private set

    fun handleEvent(event: HomeScreenEvents) {
        when (event) {
            HomeScreenEvents.LoadData -> getHomeRecords()
            HomeScreenEvents.GetIMC -> getIMC()
            HomeScreenEvents.GetCardiovascularRisk -> getCardiovascularRisk()
            HomeScreenEvents.GetHealthCareTip -> getHealthCareTip()
        }
    }

    private fun getHomeRecords() {
        viewModelScope.launch {
            val result = getHomeRecordsUseCase()
            when {
                result.isSuccess -> {
                    homeState.value = homeState.value.copy(
                        homeRecords = result.getOrNull() ?: emptyList()
                    )
                }

                result.isFailure -> {
                    println(result.exceptionOrNull()?.message)
                }
            }
        }
    }

    private fun getIMC() {
        viewModelScope.launch {
            val result = getIMCUseCase()
            when {
                result.isSuccess -> {
                    homeState.value = homeState.value.copy(
                        imcResult = result.getOrNull()
                    )
                }

                result.isFailure -> {
                    println(result.exceptionOrNull()?.message)
                }
            }
        }
    }

    private fun getCardiovascularRisk() {
        viewModelScope.launch {
            val result = hasCardiovascularRiskUserCase()
            when {
                result.isSuccess -> {
                    homeState.value = homeState.value.copy(
                        cardiovascularRisk = result.getOrNull()
                    )
                }

                result.isFailure -> {
                    println(result.exceptionOrNull()?.message)
                }
            }
        }
    }

    private fun getHealthCareTip() {
        viewModelScope.launch {
            val tipResult = getHealthCareTipUseCase()
            when {
                tipResult.isSuccess -> {
                    homeState.value = homeState.value.copy(
                        healthCareTip = tipResult.getOrNull()
                    )
                }

                tipResult.isFailure -> {
                    homeState.value = homeState.value.copy(healthCareTip = null)
                }
            }
        }
    }
}
