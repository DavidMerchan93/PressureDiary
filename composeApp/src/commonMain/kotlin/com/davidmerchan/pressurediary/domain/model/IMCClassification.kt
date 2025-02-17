package com.davidmerchan.pressurediary.domain.model

enum class IMCClassification {
    LOW_WEIGHT,
    NORMAL_WEIGHT,
    OVERWEIGHT,
    OBESE,
    DEFAULT
}

data class IMCModel(
    val classification: IMCClassification = IMCClassification.DEFAULT,
    val imc: Double,
)

