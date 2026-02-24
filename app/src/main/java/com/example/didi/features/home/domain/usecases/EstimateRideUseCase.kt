package com.example.didi.features.home.domain.usecases

import com.example.didi.features.home.domain.entities.Estimate
import com.example.didi.features.home.domain.entities.LatLng
import com.example.didi.features.home.domain.repositories.RidesRepository
import javax.inject.Inject

class EstimateRideUseCase @Inject constructor(
    private val repository: RidesRepository
) {
    suspend operator fun invoke(
        origin: LatLng?,
        destination: LatLng?
    ): Result<Estimate> {
        return try {
            if (origin == null || destination == null) {
                return Result.failure(Exception("Selecciona origen y destino"))
            }
            if (origin.lat == destination.lat && origin.lng == destination.lng) {
                return Result.failure(Exception("El origen y destino no pueden ser iguales"))
            }

            val estimate = repository.estimateRide(origin, destination)

            if (estimate.price <= 0) {
                Result.failure(Exception("No se pudo calcular una tarifa válida"))
            } else {
                Result.success(estimate)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}