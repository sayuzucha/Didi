package com.example.didi.features.home.domain.repositories

import com.example.didi.features.home.domain.entities.Estimate
import com.example.didi.features.home.domain.entities.LatLng
import com.example.didi.features.home.domain.entities.Ride
import com.example.didi.features.home.domain.entities.RideHistoryItem
import com.example.didi.features.home.domain.entities.RideStatus

interface RidesRepository {

    suspend fun estimateRide(
        origin: LatLng,
        destination: LatLng
    ): Estimate

    suspend fun createRide(
        userId: Int,
        status: RideStatus,
        totalPaid: Double
    ): Ride

    suspend fun updateRide(
        rideId: String,
        status: RideStatus,
        totalPaid: Double
    ): Ride

    suspend fun getHistory(
        userId: Int
    ): List<RideHistoryItem>
}