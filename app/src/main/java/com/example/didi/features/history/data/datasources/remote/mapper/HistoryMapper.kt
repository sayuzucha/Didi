package com.example.didi.features.history.data.datasources.remote.mapper

import com.example.didi.features.history.data.datasources.remote.models.RideHistoryItemDto
import com.example.didi.features.history.domain.entities.RideHistoryItem

fun RideHistoryItemDto.toDomain() = RideHistoryItem(
    rideId = rideId,
    date = date,
    status = status,
    totalPaid = totalPaid
)
