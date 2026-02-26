package com.example.didi.features.ride_in_progress.domain.usecases

import com.example.didi.features.ride_in_progress.domain.entities.LatLng
import com.example.didi.features.ride_in_progress.domain.entities.RideEvent
import com.example.didi.features.ride_in_progress.domain.repositories.RideInProgressRepository
import javax.inject.Inject

class StartRideTrackingUseCase @Inject constructor(
    private val repo: RideInProgressRepository
) {
    operator fun invoke(
        wsUrl: String,
        rideId: String,
        origin: LatLng,
        destination: LatLng,
        onEvent: (RideEvent) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        repo.startRideTracking(wsUrl, rideId, origin, destination, onEvent, onError)
    }
}