package com.example.didi.features.history.domain.usecases

import com.example.didi.features.history.domain.entities.RideHistoryItem
import com.example.didi.features.history.domain.repositories.HistoryRepository
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(userId: Int): Result<List<RideHistoryItem>> {
        return try {
            if (userId <= 0) {
                return Result.failure(Exception("userId inválido"))
            }

            val history = repository.getHistory(userId)

            // Filtrado: solo viajes con ID válido
            val filtered = history.filter { it.rideId.isNotBlank() }

            if (filtered.isEmpty()) {
                Result.failure(Exception("No se encontró historial de viajes"))
            } else {
                Result.success(filtered)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
