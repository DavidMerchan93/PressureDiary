package com.davidmerchan.pressurediary.data.model

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class HealthCareApi(
    private val httpClient: HttpClient
) {

    private val baseUrl = "https://64df985b71c3335b2582dab6.mockapi.io/health_care_tip"

    suspend fun getHealthCareTips(): List<TipModel> {
        val response = httpClient.get(baseUrl) {
            contentType(ContentType.Application.Json)
        }
        return response.body<List<TipModel>>()
    }
}