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
class SavedTimersViewModel @Inject constructor(
    private val repository: com.example.jjtimer.data.repository.TimerRepository
) : ViewModel() {

    val presets: StateFlow<List<TimerPreset>> = repository.presets
}
