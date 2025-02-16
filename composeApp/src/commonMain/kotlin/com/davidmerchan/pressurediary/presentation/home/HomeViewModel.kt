package com.davidmerchan.pressurediary.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.pressurediary.domain.useCase.GetHomeRecordsUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeRecordsUseCase: GetHomeRecordsUseCase
) : ViewModel() {

    var homeState = mutableStateOf(HomeScreenState())
        private set

    init {
        handleEvent(HomeScreenEvents.LoadData)
    }

    fun handleEvent(event: HomeScreenEvents) {
        viewModelScope.launch {
            val result = getHomeRecordsUseCase()
            when {
                result.isSuccess -> {
                    homeState.value = HomeScreenState(
                        isLoading = false,
                        homeRecords = result.getOrNull() ?: emptyList()
                    )
                }
                result.isFailure -> {
                    println(result.exceptionOrNull()?.message)
                }
            }
        }
    }
}
