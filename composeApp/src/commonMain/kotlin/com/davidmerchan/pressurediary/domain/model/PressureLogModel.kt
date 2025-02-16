package com.davidmerchan.pressurediary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PressureLogModel(
    val id: Long,
    val date: Long,
    val systolic: Double,
    val diastolic: Double,
    val activity: Int,
)
