package com.example.jjtimer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jjtimer.ui.theme.GlassGradientEnd
import com.example.jjtimer.ui.theme.GlassGradientStart
import com.example.jjtimer.ui.theme.NavyLight

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GlassGradientStart,
                        GlassGradientEnd
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.1f),
                        Color.Transparent
                    )
                ),
                shape = MaterialTheme.shapes.medium
            )
            .padding(1.dp) // inner border effect
    ) {
        Box(
            modifier = Modifier
                .background(NavyLight.copy(alpha = 0.5f)) // Fallback/Basis
                .padding(16.dp)
        ) {
            content()
        }
    }
}
