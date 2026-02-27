package com.example.didi.features.history.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.didi.features.history.domain.entities.RideHistoryItem
import com.example.didi.features.history.domain.usecases.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryUiState(
    val isLoading: Boolean = false,
    val history: List<RideHistoryItem> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState = _uiState.asStateFlow()

    fun loadHistory(userId: Int) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            val result = getHistoryUseCase(userId)
            
            _uiState.update { current ->
                result.fold(
                    onSuccess = { historyList ->
                        current.copy(
                            isLoading = false,
                            history = historyList
                        )
                    },
                    onFailure = { error ->
                        current.copy(
                            isLoading = false,
                            error = error.message ?: "Error desconocido"
                        )
                    }
                )
            }
        }
    }
}
