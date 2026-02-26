package com.example.didi.features.ride_in_progress.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.didi.features.ride_in_progress.domain.entities.LatLng
import com.example.didi.features.ride_in_progress.domain.usecases.StartRideTrackingUseCase
import com.example.didi.features.ride_in_progress.domain.usecases.StopRideTrackingUseCase
import com.example.didi.features.ride_in_progress.presentation.screens.RideInProgressUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

@HiltViewModel
class RideInProgressViewModel @Inject constructor(
    private val startRideTracking: StartRideTrackingUseCase,
    private val stopRideTracking: StopRideTrackingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RideInProgressUiState())
    val uiState = _uiState.asStateFlow()

    fun start(wsUrl: String, rideId: String, origin: GeoPoint, destination: GeoPoint) {
        _uiState.update { it.copy(error = null, finished = false) }

        startRideTracking(
            wsUrl = wsUrl,
            rideId = rideId,
            origin = LatLng(origin.latitude, origin.longitude),
            destination = LatLng(destination.latitude, destination.longitude),
            onEvent = { event ->
                when (event.eventType) {
                    "LOCATION_UPDATE" -> {
                        val loc = event.location
                        if (loc != null) {
                            _uiState.update {
                                it.copy(
                                    carLocation = GeoPoint(loc.lat, loc.lng),
                                    message = event.message
                                )
                            }
                        }
                    }

                    "RIDE_FINISHED" -> {
                        _uiState.update {
                            it.copy(
                                finished = true,
                                message = event.message ?: "Viaje finalizado"
                            )
                        }
                        stop()
                    }
                }
            },
            onError = { t ->
                _uiState.update { it.copy(error = t.message ?: "Error WebSocket") }
            }
        )
    }

    fun stop() {
        stopRideTracking()
    }

    override fun onCleared() {
        stop()
        super.onCleared()
    }
}