package com.example.didi.features.login.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.didi.features.login.domain.usecases.LoginUseCase
import com.example.didi.features.login.presentation.screens.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    // Función para cambiar el email
    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    // Función para cambiar la contraseña
    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    // Función de login
    fun login(email: String, password: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = loginUseCase(email, password)

            _uiState.update { current ->
                result.fold(
                    onSuccess = { current.copy(isLoading = false, success = true) },
                    onFailure = { error -> current.copy(isLoading = false, error = error.message) }
                )
            }
        }
    }
}