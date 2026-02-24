package com.example.didi.features.home.data.datasources.remote.api

package com.example.didi.features.home.data.datasources.remote.api

import com.example.didi.features.home.data.datasources.remote.models.*
import retrofit2.http.*

interface RidesApi {

    /* ===========================
       1️⃣ ESTIMATE
       POST /api/v1/rides/estimate
       =========================== */
    @POST("/api/v1/rides/estimate")
    suspend fun estimateRide(
        @Body body: EstimateRequestDto
    ): EstimateResponseDto


    /* ===========================
       2️⃣ CREATE RIDE
       POST /api/v1/rides/rides
       =========================== */
    @POST("/api/v1/rides/rides")
    suspend fun createRide(
        @Body body: CreateRideRequestDto
    ): RideResponseDto


    /* ===========================
       3️⃣ UPDATE RIDE
       PUT /api/v1/rides/rides/{ride_id}
       =========================== */
    @PUT("/api/v1/rides/rides/{ride_id}")
    suspend fun updateRide(
        @Path("ride_id") rideId: String,
        @Body body: UpdateRideRequestDto
    ): RideResponseDto


    /* ===========================
       4️⃣ HISTORY
       GET /api/v1/rides/history/{user_id}
       =========================== */
    @GET("/api/v1/rides/history/{user_id}")
    suspend fun getHistory(
        @Path("user_id") userId: Int
    ): List<RideHistoryItemDto>
}