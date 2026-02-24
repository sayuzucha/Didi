package com.example.didi.features.home.presentation.screens

import com.example.didi.features.home.domain.entities.Estimate
import com.example.didi.features.home.domain.entities.LatLng

data class HomeUiState(
    val origin: LatLng? = null,
    val destination: LatLng? = null,
    val isLoading: Boolean = false,
    val estimate: Estimate? = null,
    val isCreatingRide: Boolean = false,
    val createdRideId: String? = null,
    val error: String? = null
)