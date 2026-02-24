package com.example.didi.features.home.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class CreateRideRequestDto(

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("status")
    val status: String,

    @SerializedName("total_paid")
    val totalPaid: Double,

    @SerializedName("date")
    val date: String? = null
)