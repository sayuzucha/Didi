package com.example.didi.features.home.data.datasources.remote.mapper


import com.example.didi.features.home.data.datasources.remote.models.*
import com.example.didi.features.home.domain.entities.*

/* ===========================
   ESTIMATE
   =========================== */

fun EstimateResponseDto.toDomain(): Estimate {
    return Estimate(
        price = this.estimatedPrice,
        currency = this.currency,
        durationMinutes = this.estimatedDurationMinutes
    )
}


/* ===========================
   CREATE / UPDATE RIDE
   =========================== */

fun RideResponseDto.toDomain(): Ride {
    return Ride(
        rideId = this.rideId,
        userId = this.userId,
        date = this.date,
        status = this.status.toRideStatus(),
        totalPaid = this.totalPaid
    )
}


/* ===========================
   HISTORY
   =========================== */

fun RideHistoryItemDto.toDomain(): RideHistoryItem {
    return RideHistoryItem(
        rideId = this.rideId,
        date = this.date,
        status = this.status.toRideStatus(),
        totalPaid = this.totalPaid
    )
}


/* ===========================
   STATUS MAPPER
   =========================== */

fun String.toRideStatus(): RideStatus {
    return when (this.uppercase()) {
        "IN_PROGRESS" -> RideStatus.IN_PROGRESS
        "COMPLETED" -> RideStatus.COMPLETED
        else -> RideStatus.UNKNOWN
    }
}