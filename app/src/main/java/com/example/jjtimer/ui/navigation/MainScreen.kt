package com.example.jjtimer.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
            val showBottomBar = currentDestination?.route != Screen.TimerConfig.route

            if (showBottomBar) {
                NavigationBar(
                    containerColor = NavyLighter,
                    contentColor = Color.White
                ) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Timers") },
                        label = { Text("Timers") },
                        selected = currentDestination?.hierarchy?.any { it.route == Screen.SavedTimers.route } == true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BlueAccent,
                            selectedTextColor = BlueAccent,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = NavyDark // Hide bubble or match bg
                        ),
                        onClick = {
                            navController.navigate(Screen.SavedTimers.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
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
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
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
            startDestination = Screen.SavedTimers.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.SavedTimers.route) {
                SavedTimersScreen(
                    onNavigateToConfig = { presetId ->
                        navController.navigate(Screen.TimerConfig.createRoute(presetId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(onBack = { navController.popBackStack() })
            }
            composable(Screen.TimerConfig.route) { backStackEntry ->
                val presetId = backStackEntry.arguments?.getString("presetId")
                TimerScreen(onClose = { navController.popBackStack() })
            }
        }
    }
}
