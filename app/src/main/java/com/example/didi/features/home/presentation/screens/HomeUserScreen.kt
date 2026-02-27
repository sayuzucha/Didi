package com.example.didi.features.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Route
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.didi.features.home.presentation.components.OsmRidePickerMap
import com.example.didi.features.home.presentation.viewmodels.HomeViewModel
import org.osmdroid.util.GeoPoint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUserScreen(
    onGoToRideInProgress: (rideId: String, origin: GeoPoint, destination: GeoPoint) -> Unit,
    onGoToHistory: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showEstimateSheet by remember { mutableStateOf(false) }
    var pickingOrigin by remember { mutableStateOf(true) }

    val originGeo = uiState.origin?.let { GeoPoint(it.lat, it.lng) }
    val destGeo = uiState.destination?.let { GeoPoint(it.lat, it.lng) }


    val primary = Color(0xFF2962FF)
    val secondary = Color(0xFF448AFF)
    val backgroundTop = Color(0xFF1A237E)
    val backgroundBottom = Color(0xFF0D47A1)


    LaunchedEffect(uiState.createdRideId, originGeo, destGeo) {
        val rideId = uiState.createdRideId
        if (!rideId.isNullOrBlank() && originGeo != null && destGeo != null) {
            onGoToRideInProgress(rideId, originGeo, destGeo)
            viewModel.clearRideCreated()
        }
    }

    LaunchedEffect(originGeo) {
        if (originGeo != null && destGeo == null) pickingOrigin = false
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
                            text = "Didi Clone",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.6.sp
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White
                    ),
                    actions = {
                        IconButton(onClick = onGoToHistory) {
                            Icon(Icons.Default.History, contentDescription = "Historial", tint = Color.White)
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = if (pickingOrigin) "Selecciona ORIGEN" else "Selecciona DESTINO",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color(0xFF1B1B1B)
                                )
                                Spacer(Modifier.height(2.dp))
                                Text(
                                    text = "Toca el mapa para colocar el punto",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF6F6F6F)
                                )
                            }

                            IconButton(
                                onClick = {
                                    viewModel.clearSelection()
                                    pickingOrigin = true
                                }
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Limpiar", tint = primary)
                            }
                        }
                    }


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                    ) {
                        Box(Modifier.fillMaxSize()) {
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


                            Row(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 12.dp)
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(Color.White.copy(alpha = 0.92f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FilterChip(
                                    selected = pickingOrigin,
                                    onClick = { pickingOrigin = true },
                                    label = { Text("Origen") },
                                    leadingIcon = {
                                        Icon(Icons.Default.MyLocation, contentDescription = null)
                                    }
                                )
                                FilterChip(
                                    selected = !pickingOrigin,
                                    onClick = { pickingOrigin = false },
                                    label = { Text("Destino") },
                                    leadingIcon = {
                                        Icon(Icons.Default.Route, contentDescription = null)
                                    }
                                )
                            }
                        }
                    }

                    // Coordenadas
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                "Origen: ${uiState.origin?.lat ?: "-"}, ${uiState.origin?.lng ?: "-"}",
                                color = Color(0xFF2A2A2A)
                            )
                            Text(
                                "Destino: ${uiState.destination?.lat ?: "-"}, ${uiState.destination?.lng ?: "-"}",
                                color = Color(0xFF2A2A2A)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.estimateRide()
                            showEstimateSheet = true
                        },
                        enabled = uiState.origin != null && uiState.destination != null && !uiState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(18.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(18.dp))
                                .background(Brush.horizontalGradient(listOf(primary, secondary))),
                            contentAlignment = Alignment.Center
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                            } else {
                                Text(
                                    text = "Calcular tarifa",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                    if (uiState.error != null) {
                        Text(
                            text = uiState.error ?: "Error",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                    if (uiState.isCreatingRide) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }
                }
            }
        }

        if (showEstimateSheet) {
            ModalBottomSheet(
                onDismissRequest = { showEstimateSheet = false },
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Estimación", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

                    val estimate = uiState.estimate
                    if (uiState.isLoading && estimate == null) {
                        Text("Calculando estimación...")
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    } else if (estimate != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F7FF))
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text("Precio estimado: ${estimate.price} ${estimate.currency}", fontWeight = FontWeight.SemiBold)
                                Text("Duración estimada: ${estimate.durationMinutes} min", color = Color(0xFF4A4A4A))
                            }
                        }

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            enabled = !uiState.isCreatingRide,
                            onClick = {
                                viewModel.confirmRide(userId = 1)
                                showEstimateSheet = false
                            },
                            shape = RoundedCornerShape(18.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(Brush.horizontalGradient(listOf(primary, secondary))),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Confirmar viaje", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}
