package com.davidmerchan.pressurediary.domain.repository

interface HealthCareTipsRepository {
    suspend fun getHealthCareTips(): Result<List<String>>
}
