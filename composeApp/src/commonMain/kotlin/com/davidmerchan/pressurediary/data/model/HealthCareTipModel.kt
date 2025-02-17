package com.davidmerchan.pressurediary.data.model

import kotlinx.serialization.Serializable

@Serializable
data class HealthCareTipModel(
    val tips: List<TipModel>
)

@Serializable
data class TipModel(
    val id: String,
    val tip: String,
)
