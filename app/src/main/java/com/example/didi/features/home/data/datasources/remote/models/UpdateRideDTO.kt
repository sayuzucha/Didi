package com.example.didi.features.home.data.datasources.remote.models

data class UpdateRideRequestDto(

    @SerializedName("status")
    val status: String,

    @SerializedName("total_paid")
    val totalPaid: Double,

    @SerializedName("date")
    val date: String? = null
)