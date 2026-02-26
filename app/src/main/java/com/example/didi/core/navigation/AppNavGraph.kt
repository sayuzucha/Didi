package com.example.didi.core.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.didi.features.home.presentation.screens.HomeUserScreen
import com.example.didi.features.login.presentation.screens.LoginScreen
import com.example.didi.features.login.presentation.screens.RegisterScreen
import com.example.didi.features.ride_in_progress.presentation.screens.RideInProgressScreen // ✅ IMPORT
import org.osmdroid.util.GeoPoint // ✅ IMPORT
import androidx.compose.material3.Text // ✅ IMPORT

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    // ✅ Guardamos temporalmente origin/destination para la pantalla de tracking
    var pendingOrigin by remember { mutableStateOf<GeoPoint?>(null) }
    var pendingDestination by remember { mutableStateOf<GeoPoint?>(null) }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        composable(Routes.HOME) {
            HomeUserScreen(
                // ✅ CAMBIO: ahora Home manda rideId + origin + destination
                onGoToRideInProgress = { rideId, origin, destination ->
                    pendingOrigin = origin
                    pendingDestination = destination
                    navController.navigate(Routes.rideInProgress(rideId))
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                navController = navController,
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(navController = navController)
        }

        composable(
            route = Routes.RIDE_IN_PROGRESS,
            arguments = listOf(
                navArgument(Routes.ARG_RIDE_ID) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rideId = backStackEntry.arguments?.getString(Routes.ARG_RIDE_ID).orEmpty()

            val origin = pendingOrigin
            val destination = pendingDestination

            // ✅ AQUÍ reemplazamos el placeholder por la pantalla real del carrito
            if (origin != null && destination != null) {
                RideInProgressScreen(
                    rideId = rideId,
                    origin = origin,
                    destination = destination,
                    // ✅ emulador: 10.0.2.2 (NO localhost)
                    wsUrl = "ws://10.0.2.2:8001/api/v1/realtime/ws/cliente-android-1",
                    onRideFinishedGoHome = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            } else {
                Text("Faltan coordenadas del viaje (origen/destino).")
            }
        }
    }
}