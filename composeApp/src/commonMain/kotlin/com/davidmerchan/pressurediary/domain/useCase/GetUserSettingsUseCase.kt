package com.davidmerchan.pressurediary.domain.useCase

import com.davidmerchan.pressurediary.domain.model.UserSettingsModel
import com.davidmerchan.pressurediary.domain.repository.UserSettingsRepository

class GetUserSettingsUseCase(
    private val userSettingsRepository: UserSettingsRepository
) {
    suspend operator fun invoke(): Result<UserSettingsModel> {
        return userSettingsRepository.getUserSettings()
    }
}
