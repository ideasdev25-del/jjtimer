package com.example.jjtimer.ui.features.timer

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jjtimer.ui.components.GlassCard
import com.example.jjtimer.ui.components.PrimaryButton
import com.example.jjtimer.ui.components.TimerCircle
import com.example.jjtimer.ui.theme.NavyDark
import com.example.jjtimer.ui.theme.RedStop

@Composable
fun TimerScreen(
    onClose: () -> Unit,
    viewModel: TimerViewModel = hiltViewModel()
) {
    val phase by viewModel.phase.collectAsState()
    val time by viewModel.currentTime.collectAsState()
    val totalPhaseTime by viewModel.totalPhaseTime.collectAsState()
    val state by viewModel.timerState.collectAsState()
    val round by viewModel.currentRound.collectAsState()
    val totalRounds by viewModel.totalRounds.collectAsState()

    // Mock loading a preset if idle (In real app, pass args)
    // LaunchedEffect(Unit) { viewModel.loadPreset(300, 60, 5) }

    Scaffold(
        containerColor = NavyDark,
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Timer", color = Color.White) 
                        Text("Jiu Jitsu Timer", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Timer Display
            TimerCircle(
                totalTimeSeconds = totalPhaseTime,
                timeLeftSeconds = time,
                modeLabel = if (phase == TimerPhase.WORK) "Roll Duration" else "Rest Time"
            )

            // Info Cards (Round info)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GlassCard(modifier = Modifier.weight(1f).padding(end = 8.dp), cornerRadius = 16.dp) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("ROUND", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        Text("$round / $totalRounds", style = MaterialTheme.typography.titleLarge, color = Color.White)
                    }
                }
                GlassCard(modifier = Modifier.weight(1f).padding(start = 8.dp), cornerRadius = 16.dp) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("TOTAL TIME", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        Text("35m", style = MaterialTheme.typography.titleLarge, color = Color.White) // Mock
                    }
                }
            }

            // Controls
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PrimaryButton(
                    text = if (state == TimerState.RUNNING) "PAUSE" else "START TIMER",
                    onClick = { viewModel.toggleTimer() }
                )
                
                if (state == TimerState.PAUSED || state == TimerState.COMPLETED) {
                    TextButton(
                        onClick = { viewModel.reset() },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                         Text("RESET", color = RedStop)
                    }
                }
            }
        }
    }
}
