package com.davidmerchan.pressurediary.presentation.home

sealed interface HomeScreenEvents {
    data object LoadData : HomeScreenEvents
}
