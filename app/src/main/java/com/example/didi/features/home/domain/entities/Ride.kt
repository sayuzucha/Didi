package com.example.didi.features.home.domain.entities

data class Ride(
    val rideId: String,
    val userId: Int,
    val date: String,
    val status: RideStatus,
    val totalPaid: Double
)