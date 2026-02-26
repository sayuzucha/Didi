package com.example.didi.features.ride_in_progress.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.didi.features.ride_in_progress.presentation.components.OsmRideTrackingMap
import com.example.didi.features.ride_in_progress.presentation.viewmodels.RideInProgressViewModel
import org.osmdroid.util.GeoPoint

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

    LaunchedEffect(rideId) {
        viewModel.start(wsUrl, rideId, origin, destination)
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.stop() }
    }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Text("Viaje en curso: $rideId", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(10.dp))

        OsmRideTrackingMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            origin = origin,
            destination = destination,
            carLocation = state.carLocation
        )

        Spacer(Modifier.height(10.dp))
        state.message?.let { Text(it) }
        state.error?.let { Text("Error: $it", color = MaterialTheme.colorScheme.error) }
    }

    if (state.finished) {
        AlertDialog(
            onDismissRequest = { /* bloqueado */ },
            title = { Text("Viaje finalizado") },
            text = { Text(state.message ?: "Viaje finalizado") },
            confirmButton = {
                Button(onClick = onRideFinishedGoHome) { Text("OK") }
            }
        )
    }
}