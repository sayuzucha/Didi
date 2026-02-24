package com.example.didi.features.home.domain.usecases


import com.example.didi.features.home.domain.entities.Ride
import com.example.didi.features.home.domain.entities.RideStatus
import com.example.didi.features.home.domain.repositories.RidesRepository
import javax.inject.Inject

class CreateRideUseCase @Inject constructor(
    private val repository: RidesRepository
) {
    suspend operator fun invoke(
        userId: Int,
        initialTotalPaid: Double = 0.0
    ): Result<Ride> {
        return try {
            if (userId <= 0) {
                return Result.failure(Exception("userId inválido"))
            }
            if (initialTotalPaid < 0) {
                return Result.failure(Exception("El total no puede ser negativo"))
            }

            val ride = repository.createRide(
                userId = userId,
                status = RideStatus.IN_PROGRESS,
                totalPaid = initialTotalPaid
            )

            if (ride.rideId.isBlank()) {
                Result.failure(Exception("No se pudo crear el viaje (rideId vacío)"))
            } else {
                Result.success(ride)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}