package com.example.didi.features.history.domain.repositories

import com.example.didi.features.history.domain.entities.RideHistoryItem

interface HistoryRepository {
    suspend fun getHistory(userId: Int): List<RideHistoryItem>
}
