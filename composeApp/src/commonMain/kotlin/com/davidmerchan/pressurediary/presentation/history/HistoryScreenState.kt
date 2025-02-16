package com.davidmerchan.pressurediary.presentation.history

import com.davidmerchan.pressurediary.domain.model.PressureLogModel

data class HistoryScreenState(
    val isLoading: Boolean = false,
    val records: List<PressureLogModel>? = null,
    val error: String? = null,
)
