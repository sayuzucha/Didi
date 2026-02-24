package com.example.didi.features.home.domain.usecases

import com.example.didi.features.home.domain.entities.RideHistoryItem
import com.example.didi.features.home.domain.repositories.RidesRepository
import javax.inject.Inject

class GetRideHistoryUseCase @Inject constructor(
    private val repository: RidesRepository
) {
    suspend operator fun invoke(userId: Int): Result<List<RideHistoryItem>> {
        return try {
            if (userId <= 0) {
                return Result.failure(Exception("userId inválido"))
            }

            val history = repository.getHistory(userId)

            // Filtrado simple: ignora registros corruptos
            val filtered = history.filter { it.rideId.isNotBlank() }

            if (filtered.isEmpty()) {
                Result.failure(Exception("No hay viajes en el historial"))
            } else {
                Result.success(filtered)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}