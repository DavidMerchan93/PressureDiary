package com.davidmerchan.pressurediary.presentation.settings

import com.davidmerchan.pressurediary.domain.model.UserSettingsModel

data class SettingsScreenState(
    val userData: UserSettingsModel? = null,
    val isSaved: Boolean? = null
)
