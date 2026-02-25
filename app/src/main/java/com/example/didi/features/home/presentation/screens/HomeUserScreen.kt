package com.example.didi.features.home.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.didi.features.home.presentation.components.OsmRidePickerMap
import com.example.didi.features.home.presentation.viewmodels.HomeViewModel
import org.osmdroid.util.GeoPoint

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

    var showEstimateSheet by remember { mutableStateOf(false) }
    var pickingOrigin by remember { mutableStateOf(true) } // true = origen, false = destino

    val originGeo = uiState.origin?.let { GeoPoint(it.lat, it.lng) }
    val destGeo = uiState.destination?.let { GeoPoint(it.lat, it.lng) }

    // Si ya eligió origen, automáticamente cambia a destino
    LaunchedEffect(originGeo) {
        if (originGeo != null && destGeo == null) pickingOrigin = false
    }

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

                        // Header arriba del mapa
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = if (pickingOrigin) "Selecciona ORIGEN" else "Selecciona DESTINO",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "Toca el mapa para colocar el punto",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        // si no tienes clear en el VM, puedes setear null en tu estado o crear funciones:
                                        // viewModel.clearOriginDestination()
                                        // workaround rápido: vuelve a crear un método en el VM.
                                        // Aquí asumo que sí tienes o lo crearás.
                                        viewModel.clearSelection()
                                        pickingOrigin = true
                                    }
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "Limpiar")
                                }
                            }
                        }

                        // Mapa real
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp),
                            shape = MaterialTheme.shapes.large
                        ) {
                            OsmRidePickerMap(
                                modifier = Modifier.fillMaxSize(),
                                origin = originGeo,
                                destination = destGeo,
                                pickingOrigin = pickingOrigin,
                                onPick = { geo ->
                                    if (pickingOrigin) {
                                        viewModel.setOrigin(geo.latitude, geo.longitude)
                                        pickingOrigin = false
                                    } else {
                                        viewModel.setDestination(geo.latitude, geo.longitude)
                                    }
                                }
                            )
                        }

                        // Controles de modo
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            AssistChip(
                                onClick = { pickingOrigin = true },
                                label = { Text("Elegir Origen") },
                                leadingIcon = {
                                    Icon(Icons.Default.MyLocation, contentDescription = null)
                                }
                            )
                            AssistChip(
                                onClick = { pickingOrigin = false },
                                label = { Text("Elegir Destino") },
                                leadingIcon = {
                                    Icon(Icons.Default.Route, contentDescription = null)
                                }
                            )
                        }

                        // Coordenadas elegidas (más limpio)
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
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
        ModalBottomSheet(onDismissRequest = { showEstimateSheet = false }) {
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