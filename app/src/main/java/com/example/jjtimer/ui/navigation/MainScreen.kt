package com.example.jjtimer.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jjtimer.ui.features.profile.ProfileScreen
import com.example.jjtimer.ui.features.saved.SavedTimersScreen
import com.example.jjtimer.ui.features.timer.TimerScreen
import com.example.jjtimer.ui.theme.BlueAccent
import com.example.jjtimer.ui.theme.NavyDark
import com.example.jjtimer.ui.theme.NavyLighter

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        containerColor = NavyDark,
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            NavigationBar(
                containerColor = NavyLighter,
                contentColor = Color.White
            ) {
                // 1. Config (New Default)
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Add, contentDescription = "Setup") },
                    label = { Text("Setup") },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.TimerConfig.route } == true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BlueAccent,
                        selectedTextColor = BlueAccent,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = NavyDark
                    ),
                    onClick = {
                        navController.navigate(Screen.TimerConfig.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                // 2. Saved Timers
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Timers") },
                    label = { Text("Timers") },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.SavedTimers.route } == true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BlueAccent,
                        selectedTextColor = BlueAccent,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = NavyDark
                    ),
                    onClick = {
                        navController.navigate(Screen.SavedTimers.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                // 3. Profile
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Profile.route } == true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BlueAccent,
                        selectedTextColor = BlueAccent,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = NavyDark
                    ),
                    onClick = {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.TimerConfig.route, // New Default
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.SavedTimers.route) {
                SavedTimersScreen(
                    onNavigateToConfig = { presetId ->
                        // Navigate to config (can pass ID later for editing)
                        navController.navigate(Screen.TimerConfig.route)
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(onBack = { navController.popBackStack() })
            }
            composable(Screen.TimerConfig.route) {
                com.example.jjtimer.ui.features.config.TimerConfigScreen(
                    onSaveSuccess = {
                        // After save, go to list
                        navController.navigate(Screen.SavedTimers.route) {
                            popUpTo(Screen.TimerConfig.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
