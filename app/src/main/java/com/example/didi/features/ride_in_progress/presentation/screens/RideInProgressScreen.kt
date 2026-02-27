package com.example.didi.features.ride_in_progress.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.didi.features.ride_in_progress.presentation.components.OsmRideTrackingMap
import com.example.didi.features.ride_in_progress.presentation.viewmodels.RideInProgressViewModel
import org.osmdroid.util.GeoPoint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideInProgressScreen(
    rideId: String,
    origin: GeoPoint,
    destination: GeoPoint,
    wsUrl: String,
    onRideFinishedGoHome: () -> Unit,
    viewModel: RideInProgressViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    val backgroundTop = Color(0xFF1A237E)
    val backgroundBottom = Color(0xFF0D47A1)
    val primary = Color(0xFF2962FF)
    val secondary = Color(0xFF448AFF)

    LaunchedEffect(rideId) {
        viewModel.start(wsUrl, rideId, origin, destination)
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.stop() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(backgroundTop, backgroundBottom)))
            .statusBarsPadding()
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Seguimiento",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.6.sp
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Tarjeta de información del viaje
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = primary)
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Viaje #${rideId.takeLast(6).uppercase()}",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B1B1B)
                            )
                            Text(
                                text = "En progreso...",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF6F6F6F)
                            )
                        }
                    }
                }

                // Mapa más abajo y redondeado
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Toma el espacio disponible
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        OsmRideTrackingMap(
                            modifier = Modifier.fillMaxSize(),
                            origin = origin,
                            destination = destination,
                            carLocation = state.carLocation
                        )
                    }
                }

                // Mensajes de estado
                state.message?.let {
                    Surface(
                        color = Color.White.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = backgroundTop,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                state.error?.let {
                    Text(
                        text = "Error: $it",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                
                Spacer(Modifier.height(10.dp))
            }
        }
    }

    if (state.finished) {
        AlertDialog(
            onDismissRequest = { /* bloqueado */ },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color.White,
            title = { 
                Text(
                    "¡Llegaste a tu destino!", 
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1B1B1B)
                ) 
            },
            text = { 
                Text(
                    state.message ?: "El viaje ha finalizado con éxito.",
                    color = Color(0xFF6F6F6F)
                ) 
            },
            confirmButton = {
                Button(
                    onClick = onRideFinishedGoHome,
                    colors = ButtonDefaults.buttonColors(containerColor = primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Volver al Inicio", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}
