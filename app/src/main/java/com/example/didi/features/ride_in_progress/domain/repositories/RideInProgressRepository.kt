package com.example.didi.features.ride_in_progress.domain.repositories

import com.example.didi.features.ride_in_progress.domain.entities.LatLng
import com.example.didi.features.ride_in_progress.domain.entities.RideEvent

interface RideInProgressRepository {
    fun startRideTracking(
        wsUrl: String,
        rideId: String,
        origin: LatLng,
        destination: LatLng,
        onEvent: (RideEvent) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun stop()
}