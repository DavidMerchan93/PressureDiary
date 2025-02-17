package com.davidmerchan.pressurediary.presentation.home

sealed interface HomeScreenEvents {
    data object LoadData : HomeScreenEvents
    data object GetIMC : HomeScreenEvents
    data object GetCardiovascularRisk : HomeScreenEvents
}
