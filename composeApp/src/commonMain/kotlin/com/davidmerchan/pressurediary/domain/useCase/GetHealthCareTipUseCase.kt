package com.davidmerchan.pressurediary.domain.useCase

import com.davidmerchan.pressurediary.domain.repository.HealthCareTipsRepository

class GetHealthCareTipUseCase(
    private val healthCareTipsRepository: HealthCareTipsRepository
) {
    suspend operator fun invoke(): Result<String> {
        val tipsResponse = healthCareTipsRepository.getHealthCareTips()
        return when {
            tipsResponse.isSuccess -> {
                val tip = tipsResponse.getOrNull()?.random() ?: ""
                Result.success(tip)
            }

            tipsResponse.isFailure -> {
                Result.failure(tipsResponse.exceptionOrNull() ?: Exception("Unknown error"))
            }

            else -> {
                Result.failure(Exception("Unknown error"))
            }
        }
    }
}
