package com.example.jjtimer.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jjtimer.ui.theme.BlueAccent
import com.example.jjtimer.ui.theme.NavyLighter
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TimerCircle(
    totalTimeSeconds: Int,
    timeLeftSeconds: Int,
    modeLabel: String,
    modifier: Modifier = Modifier,
    radius: Dp = 140.dp,
    strokeWidth: Dp = 12.dp,
    activeColor: Color = BlueAccent,
    inactiveColor: Color = NavyLighter
) {
    val progress = if (totalTimeSeconds > 0) timeLeftSeconds.toFloat() / totalTimeSeconds else 0f
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "Progress")

    Box(contentAlignment = Alignment.Center, modifier = modifier.size(radius * 2)) {
        Canvas(modifier = Modifier.size(radius * 2)) {
            val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            
            // Background Circle
            drawArc(
                color = inactiveColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )

            // Progress Arc
            drawArc(
                color = activeColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                style = stroke
            )

            // Optional: Knob at the end of progress
            val angle = (-90f + 360f * animatedProgress) * (Math.PI / 180f)
            val cx = size.width / 2
            val cy = size.height / 2
            val r = (size.width - strokeWidth.toPx()) / 2
            val knobX = cx + r * cos(angle).toFloat()
            val knobY = cy + r * sin(angle).toFloat()

            drawCircle(
                color = Color.White,
                radius = strokeWidth.toPx(), // Slightly larger than stroke? Let's keep it same size
                center = Offset(knobX, knobY)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = modeLabel.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray,
                letterSpacing = 2.sp
            )
            Text(
                text = formatTimeCompact(timeLeftSeconds),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

private fun formatTimeCompact(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%02d:%02d".format(m, s)
}
