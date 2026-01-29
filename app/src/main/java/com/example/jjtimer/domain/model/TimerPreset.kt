package com.example.jjtimer.domain.model

import java.util.UUID

data class TimerPreset(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val category: String = "BJJ Standard",
    val workSeconds: Int,
    val restSeconds: Int,
    val rounds: Int,
    val colorHex: String? = null // For the side accent color
)
