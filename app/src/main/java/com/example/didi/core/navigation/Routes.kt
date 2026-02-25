package com.example.didi.core.navigation

object Routes {
    const val HOME = "home"

    const val ARG_RIDE_ID = "rideId"
    const val RIDE_IN_PROGRESS = "ride_in_progress/{$ARG_RIDE_ID}"

    fun rideInProgress(rideId: String): String = "ride_in_progress/$rideId"
}