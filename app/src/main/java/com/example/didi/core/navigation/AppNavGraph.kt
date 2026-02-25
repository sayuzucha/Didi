package com.example.didi.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.didi.features.home.presentation.screens.HomeUserScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        composable(Routes.HOME) {
            HomeUserScreen(
                onGoToRideInProgress = { rideId ->
                    navController.navigate(Routes.rideInProgress(rideId))
                }
            )
        }

        composable(
            route = Routes.RIDE_IN_PROGRESS,
            arguments = listOf(
                navArgument(Routes.ARG_RIDE_ID) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rideId = backStackEntry.arguments?.getString(Routes.ARG_RIDE_ID).orEmpty()

            // TODO: aquí pones tu RideInProgressScreen(rideId = rideId, onBack = { navController.popBackStack() })
            // Por ahora, para que compile, puedes dejar un placeholder:
            androidx.compose.material3.Text("Ride in progress: $rideId")
        }
    }
}