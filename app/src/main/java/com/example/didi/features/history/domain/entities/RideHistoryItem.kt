package com.example.didi.features.history.domain.entities

data class RideHistoryItem(
    val rideId: String,
    val date: String,
    val status: String,
    val totalPaid: Double
)
