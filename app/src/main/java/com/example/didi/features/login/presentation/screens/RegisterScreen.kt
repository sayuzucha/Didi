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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.didi.features.login.presentation.viewmodels.RegisterViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.didi.core.navigation.Routes

@Composable
fun RegisterScreen(
    navController: NavController, // NavController para la navegación
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    // Si el registro es exitoso, navegamos a la pantalla de Login
    if (state.success) {
        navController.navigate(Routes.LOGIN) {
            popUpTo(Routes.REGISTER) { inclusive = true } // Elimina Register de la pila
            launchSingleTop = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5)) // Fondo gris suave
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            // Título
            Text(
                text = "Registrarse",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5), // Azul fuerte para el título
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Nombre
            TextField(
                value = state.name,
                onValueChange = { viewModel.onNameChanged(it) }, // Almacena el valor ingresado
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Nombre") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Correo electrónico
            TextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Correo electrónico") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Contraseña
            TextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Contraseña") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Botón de registrarse
            Button(
                onClick = { viewModel.register(state.name, state.email, state.password) },
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)) // Botón azul
            ) {
                Text("Registrarse", color = Color.White, fontWeight = FontWeight.Bold)
            }

            // Indicador de carga
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }

            // Mostrar errores
            state.error?.let {
                Text(it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto para navegación a Login
            TextButton(onClick = { navController.navigate(Routes.LOGIN) }) {
                Text("¿Ya tienes cuenta? Inicia sesión aquí", color = Color(0xFF1E88E5))
            }
        }
    }
}
