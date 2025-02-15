package com.davidmerchan.pressurediary.presentation.theme.home

sealed interface HomeScreenEvents {
    data object LoadData : HomeScreenEvents
}
