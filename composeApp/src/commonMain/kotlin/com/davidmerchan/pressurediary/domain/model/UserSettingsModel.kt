package com.davidmerchan.pressurediary.domain.model

data class UserSettingsModel(
    val uid: String,
    val age: Int,
    val weight: Double,
    val height: Double,
    val smoke: Boolean,
    val gender: Int
)
