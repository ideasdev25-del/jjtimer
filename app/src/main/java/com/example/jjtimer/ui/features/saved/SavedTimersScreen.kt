package com.example.jjtimer.ui.features.saved

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jjtimer.domain.model.TimerPreset
import com.example.jjtimer.ui.components.GlassCard
import com.example.jjtimer.ui.theme.BlueAccent
import com.example.jjtimer.ui.theme.NavyDark

@Composable
fun SavedTimersScreen(
    viewModel: SavedTimersViewModel = hiltViewModel(),
    onNavigateToConfig: (String?) -> Unit
) {
    val presets by viewModel.presets.collectAsState()
    SavedTimersContent(presets = presets, onNavigateToConfig = onNavigateToConfig)
}

@Composable
fun SavedTimersContent(
    presets: List<TimerPreset>,
    onNavigateToConfig: (String?) -> Unit
) {
    Scaffold(
        containerColor = NavyDark,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToConfig(null) },
                containerColor = BlueAccent,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Timer")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Saved Timers",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(presets) { preset ->
                    TimerPresetItem(preset = preset, onClick = { onNavigateToConfig(preset.id) })
                }
            }
        }
    }
}

@Composable
fun TimerPresetItem(
    preset: TimerPreset,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = preset.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = preset.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    StatCompact("Work", formatTime(preset.workSeconds))
                    StatCompact("Rest", formatTime(preset.restSeconds))
                    StatCompact("Rounds", preset.rounds.toString())
                }
            }
            
            IconButton(
                onClick = onClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = BlueAccent.copy(alpha = 0.2f),
                    contentColor = BlueAccent
                )
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Start")
            }
        }
    }
}

@Composable
fun StatCompact(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%d:%02d".format(m, s)
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun SavedTimersScreenPreview() {
    val mockPresets = listOf(
        TimerPreset(title = "Preview Timer", workSeconds = 300, restSeconds = 60, rounds = 5)
    )
    com.example.jjtimer.ui.theme.JJTimerTheme {
        SavedTimersContent(
            presets = mockPresets,
            onNavigateToConfig = {}
        )
    }
}
