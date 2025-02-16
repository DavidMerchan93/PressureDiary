package com.davidmerchan.pressurediary.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.davidmerchan.pressurediary.presentation.history.HistoryScreen
import com.davidmerchan.pressurediary.presentation.home.HomeScreen
import com.davidmerchan.pressurediary.presentation.newRecord.NewRecordScreen
import com.davidmerchan.pressurediary.presentation.settings.SettingsScreen
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data object NewRecord : Screen

    @Serializable
    data object Settings : Screen

    @Serializable
    data object History : Screen
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            HomeScreen(
                onAddNewRecord = {
                    navController.navigate(Screen.NewRecord)
                },
                onGoToSettings = {
                    navController.navigate(Screen.Settings)
                },
                onGotToHistory = {
                    navController.navigate(Screen.History)
                }
            )
        }
        composable<Screen.NewRecord> {
            NewRecordScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable<Screen.Settings> {
            SettingsScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable<Screen.History> {
            HistoryScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}