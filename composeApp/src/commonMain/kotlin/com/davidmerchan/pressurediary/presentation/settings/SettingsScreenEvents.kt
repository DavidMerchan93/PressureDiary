package com.davidmerchan.pressurediary.presentation.theme.settings

sealed interface SettingsScreenEvents {
    data object LoadData : SettingsScreenEvents
}
