package com.davidmerchan.pressurediary.domain.model

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class PressureLogModel(
    val id: Long = Random.nextLong(),
    val date: Long,
    val systolic: Double,
    val diastolic: Double,
    val activity: Int,
)
