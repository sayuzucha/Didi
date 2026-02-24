package com.example.didi.features.home.data.repositories


import android.util.Log
import com.example.didi.features.home.data.datasources.remote.api.RidesApi
import com.example.didi.features.home.data.datasources.remote.mapper.toDomain
import com.example.didi.features.home.data.datasources.remote.models.CreateRideRequestDto
import com.example.didi.features.home.data.datasources.remote.models.EstimateRequestDto
import com.example.didi.features.home.data.datasources.remote.models.UpdateRideRequestDto
import com.example.didi.features.home.domain.entities.Estimate
import com.example.didi.features.home.domain.entities.LatLng
import com.example.didi.features.home.domain.entities.Ride
import com.example.didi.features.home.domain.entities.RideHistoryItem
import com.example.didi.features.home.domain.entities.RideStatus
import com.example.didi.features.home.domain.repositories.RidesRepository
import javax.inject.Inject

class RidesRepositoryImpl @Inject constructor(
    private val api: RidesApi
) : RidesRepository {

    override suspend fun estimateRide(origin: LatLng, destination: LatLng): Estimate {
        return try {
            val response = api.estimateRide(
                EstimateRequestDto(
                    originLat = origin.lat,
                    originLng = origin.lng,
                    destLat = destination.lat,
                    destLng = destination.lng
                )
            )
            Log.d("RidesRepository", "Estimación recibida: ${response.estimatedPrice} ${response.currency}")
            response.toDomain()
        } catch (e: Exception) {
            Log.e("RidesRepository", "Error al estimar tarifa", e)
            throw e
        }
    }

    override suspend fun createRide(userId: Int, status: RideStatus, totalPaid: Double): Ride {
        return try {
            val response = api.createRide(
                CreateRideRequestDto(
                    userId = userId,
                    status = status.name,
                    totalPaid = totalPaid
                )
            )
            Log.d("RidesRepository", "Ride creado: rideId=${response.rideId}")
            response.toDomain()
        } catch (e: Exception) {
            Log.e("RidesRepository", "Error al crear ride", e)
            throw e
        }
    }

    override suspend fun updateRide(rideId: String, status: RideStatus, totalPaid: Double): Ride {
        return try {
            val response = api.updateRide(
                rideId = rideId,
                body = UpdateRideRequestDto(
                    status = status.name,
                    totalPaid = totalPaid
                )
            )
            Log.d("RidesRepository", "Ride actualizado: rideId=${response.rideId}, status=${response.status}")
            response.toDomain()
        } catch (e: Exception) {
            Log.e("RidesRepository", "Error al actualizar ride", e)
            throw e
        }
    }

    override suspend fun getHistory(userId: Int): List<RideHistoryItem> {
        return try {
            val response = api.getHistory(userId)
            Log.d("RidesRepository", "Historial recibido: ${response.size} rides para userId=$userId")
            response.map { it.toDomain() }
        } catch (e: Exception) {
            Log.e("RidesRepository", "Error al obtener historial", e)
            throw e
        }
    }
}