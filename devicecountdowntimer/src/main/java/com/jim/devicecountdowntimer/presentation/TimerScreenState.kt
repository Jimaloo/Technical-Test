package com.jim.devicecountdowntimer.presentation

data class TimerScreenState(
    val requestsStatus: TimerRequestsStatus? = null,

    val timeRemaining: String = "00:00:00",
    val timerStatus: TimerStatus? = null
)

sealed class TimerRequestsStatus {
    object Success : TimerRequestsStatus()
    object Loading : TimerRequestsStatus()
    object Error : TimerRequestsStatus()
}