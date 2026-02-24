package com.example.didi.features.home.data.datasources.remote.models



import com.google.gson.annotations.SerializedName

data class EstimateRequestDto(

    @SerializedName("origin_lat")
    val originLat: Double,

    @SerializedName("origin_lng")
    val originLng: Double,

    @SerializedName("dest_lat")
    val destLat: Double,

    @SerializedName("dest_lng")
    val destLng: Double
)

data class EstimateResponseDto(

    @SerializedName("estimated_price")
    val estimatedPrice: Double,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("estimated_duration_minutes")
    val estimatedDurationMinutes: Int
)