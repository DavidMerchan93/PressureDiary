package com.davidmerchan.pressurediary.presentation.settings

import com.davidmerchan.pressurediary.domain.model.UserSettingsModel

sealed interface SettingsScreenEvents {
    data object LoadData : SettingsScreenEvents

    data class SaveUserSettings(
        val uid: String,
        val age: Int,
        val weight: Double,
        val height: Double,
        val smoke: Boolean,
        val gender: Gender,
    ) : SettingsScreenEvents
}
