package com.example.didi.features.ride_in_progress.domain.entities

data class LatLng(
    val lat: Double,
    val lng: Double
)

data class RideEvent(
    val eventType: String,
    val rideId: String,
    val location: LatLng?,
    val message: String?,
    val timestamp: Double?
)