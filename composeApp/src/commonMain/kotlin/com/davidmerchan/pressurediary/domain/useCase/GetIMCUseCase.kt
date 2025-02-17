package com.davidmerchan.pressurediary.domain.useCase

import com.davidmerchan.pressurediary.domain.model.IMCClassification
import com.davidmerchan.pressurediary.domain.model.IMCModel
import com.davidmerchan.pressurediary.domain.repository.UserSettingsRepository
import com.davidmerchan.pressurediary.presentation.newRecord.roundTo

class GetIMCUseCase(
    private val userSettingsRepository: UserSettingsRepository
) {
    suspend operator fun invoke(): Result<IMCModel> {
        val userData = userSettingsRepository.getUserSettings()

        return when {
            userData.isSuccess -> {
                val settings = userData.getOrNull()
                val weight = settings?.weight ?: .0
                val height = settings?.height ?: .0

                val imc = weight / (height * height)

                val imcResult = when {
                    imc < 18.5 -> IMCClassification.LOW_WEIGHT
                    imc < 25.0 -> IMCClassification.NORMAL_WEIGHT
                    imc < 30.0 -> IMCClassification.OVERWEIGHT
                    else -> IMCClassification.OBESE
                }

                Result.success(
                    IMCModel(
                        imc = imc.roundTo(2),
                        classification = imcResult
                    )
                )
            }

            else -> {
                Result.failure(
                    IllegalStateException("No se pudo obtener los datos del usuario")
                )
            }
        }

    }
}