package com.davidmerchan.pressurediary.data.repository

import com.davidmerchan.pressurediary.data.model.HealthCareApi
import com.davidmerchan.pressurediary.di.DispatcherProvider
import com.davidmerchan.pressurediary.domain.repository.HealthCareTipsRepository
import kotlinx.coroutines.withContext

class HealthCareTipsDatasource(
    private val api: HealthCareApi,
    private val dispatcher: DispatcherProvider
) : HealthCareTipsRepository {
    override suspend fun getHealthCareTips(): Result<List<String>> {
        return withContext(dispatcher.io) {
            try {
                val response = api.getHealthCareTips()
                Result.success(response.map { it.tip })
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
