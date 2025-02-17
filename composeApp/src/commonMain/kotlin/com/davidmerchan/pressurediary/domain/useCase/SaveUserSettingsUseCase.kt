package com.davidmerchan.pressurediary.domain.useCase

import com.davidmerchan.pressurediary.domain.model.UserSettingsModel
import com.davidmerchan.pressurediary.domain.repository.UserSettingsRepository

class SaveUserSettingsUseCase(
    private val userSettingsRepository: UserSettingsRepository
) {
    suspend operator fun invoke(
        uid: String,
        age: Int,
        weight: Double,
        height: Double,
        smoke: Boolean,
        gender: Int
    ): Result<Boolean> {
        return userSettingsRepository.saveUserSettings(
            UserSettingsModel(
                uid = uid,
                age = age,
                weight = weight,
                height = height,
                smoke = smoke,
                gender = gender
            )
        )
    }
}
