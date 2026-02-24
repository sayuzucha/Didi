package com.example.didi.features.home.domain.entities

data class Estimate(
    val price: Double,
    val currency: String,
    val durationMinutes: Int
)