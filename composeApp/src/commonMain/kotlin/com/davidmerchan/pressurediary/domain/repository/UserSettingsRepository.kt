package com.davidmerchan.pressurediary.domain.repository

import com.davidmerchan.pressurediary.domain.model.UserSettingsModel
import com.davidmerchan.pressurediary.presentation.settings.Gender

interface UserSettingsRepository {
    suspend fun saveUserSettings(userSettings: UserSettingsModel): Result<Boolean>
    suspend fun getUserSettings(): Result<UserSettingsModel>
}
