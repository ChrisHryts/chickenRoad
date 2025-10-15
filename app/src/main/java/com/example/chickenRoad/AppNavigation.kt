package com.example.chickenRoad

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.chickenRoad.helpers.GameResultRoute
import com.example.chickenRoad.helpers.GameRoute
import com.example.chickenRoad.helpers.LoadingRoute
import com.example.chickenRoad.helpers.MenuRoute
import com.example.chickenRoad.helpers.LegalRoute
import com.example.chickenRoad.helpers.RatingRoute
import com.example.chickenRoad.helpers.SettingsRoute
import com.example.chickenRoad.ui.screens.gameResultScreen.GameResultScreen
import com.example.chickenRoad.ui.screens.gameScreen.GameScreen
import com.example.chickenRoad.ui.screens.legalScreen.LegalScreen
import com.example.chickenRoad.ui.screens.loadingScreen.LoadingScreen
import com.example.chickenRoad.ui.screens.menuScreen.MenuScreen
import com.example.chickenRoad.ui.screens.ratingScreen.RatingScreen
import com.example.chickenRoad.ui.screens.settingsScreen.SettingsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LoadingRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<LoadingRoute> {
                LoadingScreen(onHome = {
                    navController.navigate(MenuRoute) {
                        popUpTo<LoadingRoute> { inclusive = true }
                    }
                })
            }
            composable<MenuRoute> {
                MenuScreen(
                    onGame = { navController.navigate(GameRoute) },
                    onRating = { navController.navigate(RatingRoute) },
                    onSettings = { navController.navigate(SettingsRoute) })
            }
            composable<GameRoute> {
                GameScreen(
                    onHome = {
                        navController.navigate(MenuRoute) {
                            popUpTo<GameRoute> { inclusive = true }
                        }
                    },
                    onSettings = {
                        navController.navigate(SettingsRoute) {
                            popUpTo<GameRoute> { inclusive = true }
                        }
                    },
                    onGameResult = { isWin, time ->
                        navController.navigate(GameResultRoute(isWin, time)) {
                            popUpTo<GameRoute> { inclusive = true }
                        }
                    })
            }
            composable<GameResultRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<GameResultRoute>()
                GameResultScreen(
                    isWin = args.isWin,
                    time = args.time,
                    onHome = {
                        navController.navigate(MenuRoute) {
                            popUpTo<GameResultRoute> { inclusive = true }
                        }
                    },
                    onGame = {
                        navController.navigate(GameRoute) {
                            popUpTo<GameResultRoute> { inclusive = true }
                        }
                    })
            }
            composable<RatingRoute> {
                RatingScreen(onBack = { navController.popBackStack() })
            }
            composable<SettingsRoute> {
                SettingsScreen(
                    onLegal = { type -> navController.navigate(LegalRoute(type)) },
                    onBack = { navController.popBackStack() })
            }
            composable<LegalRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<LegalRoute>()
                LegalScreen(type = args.type)
            }
        }
    }
}
