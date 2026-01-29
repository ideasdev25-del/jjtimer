package com.example.jjtimer.ui.features.timer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.jjtimer.ui.components.GlassCard
import com.example.jjtimer.ui.components.PrimaryButton
import com.example.jjtimer.ui.components.TimerCircle
import com.example.jjtimer.ui.theme.BlueAccent
import com.example.jjtimer.ui.theme.NavyDark
import com.example.jjtimer.ui.theme.RedStop

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        containerColor = NavyDark,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .statusBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                // Back Button
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .background(Color.White.copy(alpha = 0.1f), CircleShape)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.KeyboardArrowDown,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }

                // Centered Capsule
                Surface(
                    color = Color.White.copy(alpha = 0.05f),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
                ) {
                    Text(
                        text = "Jiu Jitsu Timer",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }

                // Settings Gear
                IconButton(
                    onClick = { /* Settings */ },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .background(Color.White.copy(alpha = 0.1f), CircleShape)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Main Timer Display
            TimerCircle(
                totalTimeSeconds = totalPhaseTime,
                timeLeftSeconds = time,
                modeLabel = if (phase == TimerPhase.WORK) "ROLL DURATION" else "REST TIME"
            )

            Spacer(modifier = Modifier.weight(1f))

            // Support Cards (Rest Time & Rounds Side-by-Side)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Rest Time Card
                GlassCard(
                    modifier = Modifier.weight(1f),
                    cornerRadius = 24.dp
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "REST TIME",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.5f)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            // Hourglass icon could go here
                        }
                        Text(
                            text = formatTimeCompact(60), // Example rest time
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // Rounds Card
                GlassCard(
                    modifier = Modifier.weight(1f),
                    cornerRadius = 24.dp
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "ROUNDS",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.5f)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(onClick = {}, modifier = Modifier.size(24.dp).background(Color.White.copy(alpha = 0.1f), CircleShape)) {
                                Icon(androidx.compose.material.icons.Icons.Default.Remove, "", tint = Color.White, modifier = Modifier.size(16.dp))
                            }
                            Text(
                                text = totalRounds.toString(),
                                modifier = Modifier.padding(horizontal = 12.dp),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            IconButton(onClick = {}, modifier = Modifier.size(24.dp).background(BlueAccent, CircleShape)) {
                                Icon(Icons.Default.Add, "", tint = Color.White, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }

            // Stats Footer Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(androidx.compose.material.icons.Icons.Default.Timer, "", tint = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Total: 35m", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.5f))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(androidx.compose.material.icons.Icons.Default.VolumeUp, "", tint = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sound On", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.5f))
                }
            }

            // Primary Control Button
            PrimaryButton(
                text = if (state == TimerState.RUNNING) "PAUSE" else "START TIMER",
                onClick = { viewModel.toggleTimer() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
        }
    }
}

fun formatTimeCompact(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%02d:%02d".format(m, s)
}
