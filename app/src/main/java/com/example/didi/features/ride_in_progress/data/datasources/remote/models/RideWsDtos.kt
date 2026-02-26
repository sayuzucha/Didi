package com.example.didi.features.ride_in_progress.data.datasources.remote.models


import com.google.gson.annotations.SerializedName

data class LatLngDto(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)

data class RideStartMessageDto(
    @SerializedName("ride_id") val rideId: String,
    @SerializedName("origin") val origin: LatLngDto,
    @SerializedName("destination") val destination: LatLngDto
)

data class RideEventDto(
    @SerializedName("event_type") val eventType: String,
    @SerializedName("ride_id") val rideId: String,
    @SerializedName("location") val location: LatLngDto?,
    @SerializedName("message") val message: String?,
    @SerializedName("timestamp") val timestamp: Double?
)