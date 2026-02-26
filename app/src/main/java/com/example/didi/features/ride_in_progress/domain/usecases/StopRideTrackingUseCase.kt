package com.example.didi.features.ride_in_progress.domain.usecases

import com.example.didi.features.ride_in_progress.domain.repositories.RideInProgressRepository
import javax.inject.Inject

class StopRideTrackingUseCase @Inject constructor(
    private val repo: RideInProgressRepository
) {
    operator fun invoke() = repo.stop()
}

