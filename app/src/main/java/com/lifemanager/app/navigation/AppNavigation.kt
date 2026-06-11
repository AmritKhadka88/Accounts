package com.lifemanager.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lifemanager.app.ui.screens.*

sealed class Screen(val route: String, val title: String) {
    object Dashboard : Screen("dashboard", "Dashboard")
    object Finance : Screen("finance", "Finance")
    object Vehicle : Screen("vehicle", "Vehicle")
    object Notes : Screen("notes", "Notes")
    object More : Screen("more", "More")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Dashboard,
        Screen.Finance,
        Screen.Vehicle,
        Screen.Notes,
        Screen.More
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            when (screen) {
                                Screen.Dashboard -> Icon(Icons.Filled.Home, contentDescription = null)
                                Screen.Finance -> Icon(Icons.Filled.AccountBalance, contentDescription = null)
                                Screen.Vehicle -> Icon(Icons.Filled.DirectionsCar, contentDescription = null)
                                Screen.Notes -> Icon(Icons.Filled.Note, contentDescription = null)
                                Screen.More -> Icon(Icons.Filled.MoreHoriz, contentDescription = null)
                            }
                        },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) { DashboardScreen() }
            composable(Screen.Finance.route) { FinanceScreen() }
            composable(Screen.Vehicle.route) { VehicleScreen() }
            composable(Screen.Notes.route) { NotesScreen() }
            composable(Screen.More.route) { MoreScreen() }
        }
    }
}
