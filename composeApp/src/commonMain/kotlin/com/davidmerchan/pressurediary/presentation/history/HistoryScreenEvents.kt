package com.davidmerchan.pressurediary.presentation.theme.history

sealed interface HistoryScreenEvents {
    data object LoadData : HistoryScreenEvents
}
