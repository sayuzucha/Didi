package com.example.didi.features.ride_in_progress.data.repositories

import com.example.didi.features.ride_in_progress.data.datasources.remote.api.RideWsClient
import com.example.didi.features.ride_in_progress.data.datasources.remote.models.LatLngDto
import com.example.didi.features.ride_in_progress.data.datasources.remote.models.RideStartMessageDto
import com.example.didi.features.ride_in_progress.domain.entities.LatLng
import com.example.didi.features.ride_in_progress.domain.entities.RideEvent
import com.example.didi.features.ride_in_progress.domain.repositories.RideInProgressRepository
import javax.inject.Inject

class RideInProgressRepositoryImpl @Inject constructor(
    private val wsClient: RideWsClient
) : RideInProgressRepository {

    override fun startRideTracking(
        wsUrl: String,
        rideId: String,
        origin: LatLng,
        destination: LatLng,
        onEvent: (RideEvent) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val start = RideStartMessageDto(
            rideId = rideId,
            origin = LatLngDto(origin.lat, origin.lng),
            destination = LatLngDto(destination.lat, destination.lng)
        )

        wsClient.connect(
            url = wsUrl,
            startMessage = start,
            onEvent = onEvent,
            onError = onError
        )
    }

    override fun stop() {
        wsClient.disconnect()
    }
}