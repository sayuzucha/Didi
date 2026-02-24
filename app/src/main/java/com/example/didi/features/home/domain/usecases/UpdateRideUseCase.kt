package com.example.didi.features.home.domain.usecases

import com.example.didi.features.home.domain.entities.Ride
import com.example.didi.features.home.domain.entities.RideStatus
import com.example.didi.features.home.domain.repositories.RidesRepository
import javax.inject.Inject

class UpdateRideUseCase @Inject constructor(
    private val repository: RidesRepository
) {
    suspend operator fun invoke(
        rideId: String,
        totalPaid: Double,
        status: RideStatus = RideStatus.COMPLETED
    ): Result<Ride> {
        return try {
            if (rideId.isBlank()) {
                return Result.failure(Exception("rideId inválido"))
            }
            if (totalPaid < 0) {
                return Result.failure(Exception("El total no puede ser negativo"))
            }

            val updated = repository.updateRide(
                rideId = rideId,
                status = status,
                totalPaid = totalPaid
            )

            Result.success(updated)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}