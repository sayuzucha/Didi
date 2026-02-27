package com.example.didi.features.history.data.repositories

import android.util.Log
import com.example.didi.features.history.data.datasources.remote.api.HistoryApi
import com.example.didi.features.history.data.datasources.remote.mapper.toDomain
import com.example.didi.features.history.domain.entities.RideHistoryItem
import com.example.didi.features.history.domain.repositories.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val api: HistoryApi
) : HistoryRepository {

    override suspend fun getHistory(userId: Int): List<RideHistoryItem> {
        return try {
            val response = api.getHistory(userId)
            Log.d("HistoryRepository", "Historial recibido: ${response.size} viajes para userId=$userId")
            response.map { it.toDomain() }
        } catch (e: Exception) {
            Log.e("HistoryRepository", "Error al obtener historial de viajes", e)
            throw e
        }
    }
}
