package com.example.didi.features.login.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.navigation.NavController
import com.example.didi.core.navigation.Routes
import com.example.didi.features.login.presentation.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController, // Recibimos el NavController
    onNavigateToRegister: () -> Unit, // Callback para ir al registro
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Fondo gris suave
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "Iniciar sesión",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Correo electrónico
            TextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF1E88E5),
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            // Contraseña
            TextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF1E88E5),
                    unfocusedIndicatorColor = Color.Gray
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            // Botón de Iniciar sesión
            Button(
                onClick = {
                    viewModel.login(state.email, state.password) // Llamar al login
                },
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Iniciar sesión", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            // Si hay error, lo mostramos
            state.error?.let {
                Text(it, color = Color.Red, modifier = Modifier.padding(top = 16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para navegar a la pantalla de registro
            TextButton(onClick = { onNavigateToRegister() }) {
                Text("¿No tienes cuenta? Regístrate aquí.", color = Color(0xFF1E88E5))
            }
        }
    }

    // Si el login es exitoso, navegar a HOME
    if (state.success) {
        navController.navigate(Routes.HOME) {
            popUpTo(Routes.LOGIN) { inclusive = true } // Elimina la pantalla de login del stack
            launchSingleTop = true
        }
    }
}