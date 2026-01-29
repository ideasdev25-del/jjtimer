package com.example.jjtimer.ui.features.profile

import androidx.lifecycle.ViewModel
import com.example.jjtimer.domain.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile.asStateFlow()

    init {
        // Mock Data
        _profile.value = UserProfile(
            id = "user_123",
            name = "Rickson Gracie",
            email = "rickson@bjj.com",
            rank = "Faixa Preta",
            rankDegree = 4,
            sinceYear = 2018,
            timeRolledHours = 120,
            totalRounds = 450
        )
    }
}
