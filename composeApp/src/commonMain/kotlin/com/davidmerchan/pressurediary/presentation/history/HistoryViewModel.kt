package com.davidmerchan.pressurediary.presentation.theme.history

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.pressurediary.domain.useCase.GetAllRecordsUseCase
import com.davidmerchan.pressurediary.presentation.history.HistoryScreenState
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val getAllRecordsUseCase: GetAllRecordsUseCase
) : ViewModel() {

    var historyState = mutableStateOf(HistoryScreenState())
        private set

    fun handleEvent(event: HistoryScreenEvents) {
        when (event) {
            HistoryScreenEvents.LoadData -> {
                getAllRecords()
            }
        }
    }

    private fun getAllRecords() {
        historyState.value = HistoryScreenState(isLoading = true)

        viewModelScope.launch {
            val records = getAllRecordsUseCase()
            historyState.value = when {
                records.isSuccess -> {
                    HistoryScreenState(records = records.getOrNull())
                }

                records.isFailure -> {
                    HistoryScreenState(error = records.exceptionOrNull()?.message)
                }

                else -> {
                    HistoryScreenState()
                }
            }
        }
    }
}
