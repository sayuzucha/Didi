package com.example.didi.features.home.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.didi.features.home.presentation.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUserScreen(
    onGoToRideInProgress: (rideId: String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Navegar cuando ya se creó el ride
    LaunchedEffect(uiState.createdRideId) {
        val rideId = uiState.createdRideId
        if (!rideId.isNullOrBlank()) {
            onGoToRideInProgress(rideId)
            viewModel.clearRideCreated()
        }
    }

    // Modal de estimación
    var showEstimateSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Home (User)") },
                actions = {
                    IconButton(onClick = { viewModel.estimateRide() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Re-estimar")
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                uiState.error != null -> {
                    Text(
                        text = uiState.error ?: "Error",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red
                    )
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        // Placeholder del mapa (por ahora)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            shape = MaterialTheme.shapes.large
                        ) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Mapa aquí (elige origen y destino)")
                            }
                        }

                        // Acciones rápidas para simular selección (mientras implementas mapa real)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                modifier = Modifier.weight(1f),
                                onClick = { viewModel.setOrigin(-34.6037, -58.3816) }
                            ) {
                                Icon(Icons.Default.MyLocation, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Origen")
                            }

                            OutlinedButton(
                                modifier = Modifier.weight(1f),
                                onClick = { viewModel.setDestination(-34.6158, -58.4333) }
                            ) {
                                Icon(Icons.Default.Route, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Destino")
                            }
                        }

                        // Mostrar coordenadas elegidas
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("Origen: ${uiState.origin?.lat ?: "-"}, ${uiState.origin?.lng ?: "-"}")
                                Text("Destino: ${uiState.destination?.lat ?: "-"}, ${uiState.destination?.lng ?: "-"}")
                            }
                        }

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = uiState.origin != null && uiState.destination != null && !uiState.isLoading,
                            onClick = {
                                viewModel.estimateRide()
                                showEstimateSheet = true
                            }
                        ) {
                            Text("Calcular tarifa")
                        }

                        if (uiState.isCreatingRide) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }

    // BottomSheet de estimación
    if (showEstimateSheet) {
        ModalBottomSheet(
            onDismissRequest = { showEstimateSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Estimación", style = MaterialTheme.typography.titleLarge)

                val estimate = uiState.estimate
                if (estimate == null) {
                    Text("Calculando estimación...")
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                } else {
                    Text("Precio estimado: ${estimate.price} ${estimate.currency}")
                    Text("Duración estimada: ${estimate.durationMinutes} min")

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !uiState.isCreatingRide,
                        onClick = {
                            // aquí usa el userId de tu sesión (login falso). Por ahora te dejo un placeholder.
                            // Cambia 1 por session.userId cuando lo tengas.
                            viewModel.confirmRide(userId = 1)
                            showEstimateSheet = false
                        }
                    ) {
                        Text("Confirmar viaje")
                    }
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}