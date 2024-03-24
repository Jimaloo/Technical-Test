package com.jim.countdowntimer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jim.devicecountdowntimer.presentation.TimerScreen
import com.jim.devicecountdowntimer.presentation.TimerScreenUiEvent
import com.jim.devicecountdowntimer.presentation.TimerScreenViewModel

@Composable
fun CountdownTimer() {

    val viewModel: TimerScreenViewModel = viewModel<TimerScreenViewModel>()

    LaunchedEffect(key1 = true, block = {
        viewModel.onEventChange(TimerScreenUiEvent.StartTimer)
    })

    TimerScreen(state = viewModel.state.value)
}