package com.example.didi.features.home.domain.entities

data class RideHistoryItem(
    val rideId: String,
    val date: String,
    val status: RideStatus,
    val totalPaid: Double
)