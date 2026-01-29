package com.example.jjtimer.ui.features.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jjtimer.domain.model.TimerPreset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SavedTimersViewModel @Inject constructor() : ViewModel() {

    private val _presets = MutableStateFlow<List<TimerPreset>>(emptyList())
    val presets: StateFlow<List<TimerPreset>> = _presets.asStateFlow()

    init {
        // Load Mock Data
        _presets.value = listOf(
            TimerPreset(
                title = "Competition Prep",
                category = "IBJJF Standard",
                workSeconds = 300, // 5:00
                restSeconds = 60,  // 1:00
                rounds = 10,
                colorHex = "#1E88E5" // Blue
            ),
            TimerPreset(
                title = "Light Drilling",
                category = "Flow & Technique",
                workSeconds = 180, // 3:00
                restSeconds = 30,
                rounds = 100, // Infinite
                colorHex = "#00C853" // Green
            ),
            TimerPreset(
                title = "Shark Tank",
                category = "High Intensity",
                workSeconds = 120, // 2:00
                restSeconds = 10,
                rounds = 5,
                colorHex = "#D50000" // Red
            )
        )
    }
}
