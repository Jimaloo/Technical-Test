package com.jim.devicecountdowntimer.presentation

import com.jim.devicecountdowntimer.domain.CountdownTimerRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TimerScreenViewModelTest {

    private lateinit var viewModel: TimerScreenViewModel

    @MockK
    private lateinit var repository: CountdownTimerRepository
    val expectedTimerState = TimerScreenState(
        requestsStatus = TimerRequestsStatus.Success,
        timeRemaining = "05:00:00",
        timerStatus = TimerStatus.ACTIVE
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockKAnnotations.init(this, relaxed = true)
        coEvery { repository.getTimerState() } returns flowOf(expectedTimerState)

        viewModel = TimerScreenViewModel(repository)
    }


    @Test
    fun `startTimer should update state with loading status`() = runTest {
        viewModel.onEventChange(TimerScreenUiEvent.StartTimer)
        advanceUntilIdle()
        assertEquals(expectedTimerState.timerStatus, viewModel.state.value.timerStatus)
    }

    @Test
    fun `startTimer should update state with Timer data when repository emits data`() = runTest {

        coEvery { repository.getTimerState() } returns flowOf(expectedTimerState)

        viewModel.onEventChange(TimerScreenUiEvent.StartTimer)
        advanceUntilIdle()
        assertEquals(expectedTimerState.timeRemaining, viewModel.state.value.timeRemaining)
        assertEquals(expectedTimerState.timerStatus, viewModel.state.value.timerStatus)
        assertEquals(TimerRequestsStatus.Success, viewModel.state.value.requestsStatus)
    }


}