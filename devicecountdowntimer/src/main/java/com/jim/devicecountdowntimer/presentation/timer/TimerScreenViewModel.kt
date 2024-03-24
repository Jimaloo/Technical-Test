package com.jim.devicecountdowntimer.presentation.timer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jim.devicecountdowntimer.domain.CountdownTimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerScreenViewModel @Inject constructor(
    private val repository: CountdownTimerRepository
) : ViewModel() {

    private val _state = mutableStateOf(TimerScreenState())
    val state: State<TimerScreenState> = _state

    fun onEventChange(event: TimerScreenUiEvent) {
        when (event) {
            is TimerScreenUiEvent.StartTimer -> {
                startTimer()
            }
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                requestsStatus = TimerRequestsStatus.Loading
            )

            repository.getTimerState().collect{
                _state.value = state.value.copy(
                    timeRemaining = it.timeRemaining,
                    timerStatus = it.timerStatus,
                    requestsStatus = TimerRequestsStatus.Success
                )
            }
        }
    }
}