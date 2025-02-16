package com.davidmerchan.pressurediary.presentation.home

import com.davidmerchan.pressurediary.domain.model.PressureLogModel

data class HomeScreenState(
    val isLoading: Boolean = true,
    val homeRecords: List<PressureLogModel> = emptyList(),
)
