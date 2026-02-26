package com.example.didi.features.ride_in_progress.data.datasources.remote.mapper

import com.example.didi.features.ride_in_progress.data.datasources.remote.models.LatLngDto
import com.example.didi.features.ride_in_progress.data.datasources.remote.models.RideEventDto
import com.example.didi.features.ride_in_progress.domain.entities.LatLng
import com.example.didi.features.ride_in_progress.domain.entities.RideEvent

fun LatLngDto.toDomain(): LatLng = LatLng(lat = lat, lng = lng)

fun RideEventDto.toDomain(): RideEvent = RideEvent(
    eventType = eventType,
    rideId = rideId,
    location = location?.toDomain(),
    message = message,
    timestamp = timestamp
)

