package com.example.didi.features.history.data.datasources.remote.api

import com.example.didi.features.history.data.datasources.remote.models.RideHistoryItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryApi {
    @GET("rides/history/{user_id}")
    suspend fun getHistory(@Path("user_id") userId: Int): List<RideHistoryItemDto>
}
