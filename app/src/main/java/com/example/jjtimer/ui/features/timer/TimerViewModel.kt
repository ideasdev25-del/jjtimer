package com.example.jjtimer.ui.features.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class TimerState {
    IDLE, RUNNING, PAUSED, COMPLETED
}

enum class TimerPhase {
    WORK, REST
}

@HiltViewModel
class TimerViewModel @Inject constructor() : ViewModel() {

    private val _timerState = MutableStateFlow(TimerState.IDLE)
    val timerState = _timerState.asStateFlow()

    private val _phase = MutableStateFlow(TimerPhase.WORK)
    val phase = _phase.asStateFlow()

    private val _currentTime = MutableStateFlow(300) // seconds
    val currentTime = _currentTime.asStateFlow()

    private val _totalPhaseTime = MutableStateFlow(300)
    val totalPhaseTime = _totalPhaseTime.asStateFlow()

    private val _currentRound = MutableStateFlow(1)
    val currentRound = _currentRound.asStateFlow()

    private val _totalRounds = MutableStateFlow(5)
    val totalRounds = _totalRounds.asStateFlow()
    
    // Config params
    private var workTimeConfig = 300
    private var restTimeConfig = 60

    private var timerJob: Job? = null

    fun loadPreset(work: Int, rest: Int, rounds: Int) {
        workTimeConfig = work
        restTimeConfig = rest
        _totalRounds.value = rounds
        reset()
    }

    fun toggleTimer() {
        if (_timerState.value == TimerState.RUNNING) {
            pause()
        } else {
            start()
        }
    }

    private fun start() {
        _timerState.value = TimerState.RUNNING
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_currentTime.value > 0) {
                delay(1000L)
                _currentTime.value -= 1
            }
            onPhaseComplete()
        }
    }

    private fun pause() {
        _timerState.value = TimerState.PAUSED
        timerJob?.cancel()
    }

    private fun onPhaseComplete() {
        when (_phase.value) {
            TimerPhase.WORK -> {
                 if (_currentRound.value < _totalRounds.value) {
                     // Go to Rest
                     _phase.value = TimerPhase.REST
                     _totalPhaseTime.value = restTimeConfig
                     _currentTime.value = restTimeConfig
                     start() // Auto start rest? Usually yes for intervals
                 } else {
                     _timerState.value = TimerState.COMPLETED
                 }
            }
            TimerPhase.REST -> {
                // Next Round
                _currentRound.value += 1
                _phase.value = TimerPhase.WORK
                _totalPhaseTime.value = workTimeConfig
                _currentTime.value = workTimeConfig
                start()
            }
        }
    }

    fun reset() {
        timerJob?.cancel()
        _timerState.value = TimerState.IDLE
        _phase.value = TimerPhase.WORK
        _currentRound.value = 1
        _totalPhaseTime.value = workTimeConfig
        _currentTime.value = workTimeConfig
    }
}
