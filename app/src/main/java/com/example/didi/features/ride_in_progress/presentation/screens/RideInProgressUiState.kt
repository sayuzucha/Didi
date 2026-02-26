package com.example.didi.features.ride_in_progress.presentation.screens


import org.osmdroid.util.GeoPoint

data class RideInProgressUiState(
    val carLocation: GeoPoint? = null,
    val finished: Boolean = false,
    val message: String? = null,
    val error: String? = null
)