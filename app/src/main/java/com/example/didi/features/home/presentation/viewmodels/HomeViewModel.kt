package com.example.didi.features.home.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.didi.features.home.domain.entities.Estimate
import com.example.didi.features.home.domain.entities.LatLng
import com.example.didi.features.home.domain.usecases.CreateRideUseCase
import com.example.didi.features.home.domain.usecases.EstimateRideUseCase
import com.example.didi.features.home.presentation.screens.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val estimateRideUseCase: EstimateRideUseCase,
    private val createRideUseCase: CreateRideUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun setOrigin(lat: Double, lng: Double) {
        _uiState.update { it.copy(origin = LatLng(lat, lng)) }
    }

    fun setDestination(lat: Double, lng: Double) {
        _uiState.update { it.copy(destination = LatLng(lat, lng)) }
    }

    fun estimateRide() {
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = estimateRideUseCase(
                origin = _uiState.value.origin,
                destination = _uiState.value.destination
            )

            _uiState.update { current ->
                result.fold(
                    onSuccess = { estimate: Estimate ->
                        current.copy(
                            isLoading = false,
                            estimate = estimate
                        )
                    },
                    onFailure = { error ->
                        current.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                )
            }
        }
    }

    fun confirmRide(userId: Int) {
        _uiState.update { it.copy(isCreatingRide = true, error = null) }

        viewModelScope.launch {
            val result = createRideUseCase(userId)

            _uiState.update { current ->
                result.fold(
                    onSuccess = { ride ->
                        current.copy(
                            isCreatingRide = false,
                            createdRideId = ride.rideId
                        )
                    },
                    onFailure = { error ->
                        current.copy(
                            isCreatingRide = false,
                            error = error.message
                        )
                    }
                )
            }
        }
    }

    fun clearRideCreated() {
        _uiState.update { it.copy(createdRideId = null) }
    }
    fun clearSelection() {
        _uiState.update { it.copy(origin = null, destination = null, estimate = null) }
    }
}