package com.example.didi.features.home.data.datasources.remote.api


import com.example.didi.features.home.data.datasources.remote.models.*
import retrofit2.http.*

interface RidesApi {

    @POST("rides/estimate")
    suspend fun estimateRide(@Body body: EstimateRequestDto): EstimateResponseDto

    @POST("rides/rides")
    suspend fun createRide(@Body body: CreateRideRequestDto): RideResponseDto

    @PUT("rides/rides/{ride_id}")
    suspend fun updateRide(
        @Path("ride_id") rideId: String,
        @Body body: UpdateRideRequestDto
    ): RideResponseDto

    @GET("rides/history/{user_id}")
    suspend fun getHistory(@Path("user_id") userId: Int): List<RideHistoryItemDto>
}