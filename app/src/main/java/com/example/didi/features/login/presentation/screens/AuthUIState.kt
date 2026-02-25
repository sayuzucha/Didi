package com.example.didi.features.login.presentation.screens

// Estado de la pantalla de Login
data class LoginUiState(
    val email: String = "",         // Campo para el email
    val password: String = "",      // Campo para la contraseña
    val isLoading: Boolean = false, // Indicador de carga
    val success: Boolean = false,   // Indicador de éxito
    val error: String? = null       // Mensaje de error
)

// Estado de la pantalla de Registro
data class RegisterUiState(
    val name: String = "",          // Campo para el nombre completo
    val email: String = "",         // Campo para el correo electrónico
    val password: String = "",      // Campo para la contraseña
    val isLoading: Boolean = false, // Indicador de carga
    val success: Boolean = false,   // Indicador de éxito
    val error: String? = null       // Mensaje de error
)