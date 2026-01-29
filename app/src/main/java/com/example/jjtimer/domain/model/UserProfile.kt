package com.example.jjtimer.domain.model

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val rank: String, // e.g. "Black Belt"
    val rankDegree: Int,
    val sinceYear: Int,
    val timeRolledHours: Int,
    val totalRounds: Int,
    val avatarUrl: String? = null
)
