package com.example.didi.features.home.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class RideHistoryItemDto(

    @SerializedName("ride_id")
    val rideId: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("total_paid")
    val totalPaid: Double
)