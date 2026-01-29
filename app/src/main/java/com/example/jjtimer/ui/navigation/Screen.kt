package com.example.jjtimer.ui.navigation

sealed class Screen(val route: String) {
    object SavedTimers : Screen("saved_timers")
    object Profile : Screen("profile")
    object TimerConfig : Screen("timer_config?presetId={presetId}") {
        fun createRoute(presetId: String? = null) = "timer_config?presetId=${presetId ?: ""}"
    }
    object TimerRunning : Screen("timer_running/{presetId}") {
        fun createRoute(presetId: String) = "timer_running/$presetId"
    }
}
