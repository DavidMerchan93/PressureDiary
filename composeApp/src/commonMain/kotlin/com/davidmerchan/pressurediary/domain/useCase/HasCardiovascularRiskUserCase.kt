package com.davidmerchan.pressurediary.domain.useCase

import com.davidmerchan.pressurediary.domain.model.CardiovascularRiskModel
import com.davidmerchan.pressurediary.domain.repository.PressureLogRepository
import com.davidmerchan.pressurediary.domain.repository.UserSettingsRepository

class HasCardiovascularRiskUserCase(
    private val userSettingsRepository: UserSettingsRepository,
    private val getIMCUseCase: GetIMCUseCase,
    private val pressureLogRepository: PressureLogRepository
) {
    suspend operator fun invoke(): Result<CardiovascularRiskModel> {
        val userData = userSettingsRepository.getUserSettings()
        val imcResult = getIMCUseCase()
        val lastPressureLog = pressureLogRepository.getLastPressureLog()

        return when {
            userData.isSuccess && imcResult.isSuccess && lastPressureLog.isSuccess -> {
                val settings = userData.getOrNull()
                val imc = imcResult.getOrNull()
                val lastPressure = lastPressureLog.getOrNull()

                if (settings != null && imc != null && lastPressure != null) {
                    val result = calculateCardiovascularRisk(
                        imc = imc.imc,
                        age = settings.age,
                        gender = settings.gender,
                        isSmoke = settings.smoke,
                        pressSystolic = lastPressure.systolic,
                        pressDiastolic = lastPressure.diastolic
                    )
                    Result.success(result)
                } else {
                    Result.failure(IllegalStateException("No se pudo obtener los datos del usuario"))
                }
            }

            else -> {
                Result.failure(IllegalStateException("No se pudo obtener los datos del usuario"))
            }
        }
    }

    private fun calculateCardiovascularRisk(
        imc: Double,
        age: Int,
        gender: Int,
        isSmoke: Boolean,
        pressSystolic: Double,
        pressDiastolic: Double
    ): CardiovascularRiskModel {
        var score = 0

        // Puntaje por edad
        score += when {
            age >= 55 -> 2
            age in 45 until 55 -> 1
            else -> 0
        }

        // Puntaje por género (algunos estudios indican mayor riesgo en varones)
        score += if (gender == 1) 2 else 1

        // Puntaje por hábito de fumar
        if (isSmoke) score += 2

        // Puntaje por presión sistólica
        score += when {
            pressSystolic > 140 -> 2
            pressSystolic in 130.0..140.0 -> 1
            else -> 0
        }

        // Puntaje por presión diastólica
        score += when {
            pressDiastolic > 90 -> 2
            pressDiastolic in 80.0..90.0 -> 1
            else -> 0
        }

        // Puntaje por IMC
        score += when {
            imc > 30 -> 2
            imc > 25 -> 1
            else -> 0
        }

        // Umbral simplificado para determinar riesgo
        return when (score) {
            in 0..3 -> CardiovascularRiskModel.LOW
            in 4..5 -> CardiovascularRiskModel.MEDIUM
            in 6..7 -> CardiovascularRiskModel.HIGH
            in 8..10 -> CardiovascularRiskModel.VERY_HIGH
            else -> {
                CardiovascularRiskModel.NOT_APPLICABLE
            }
        }
    }
}
