package com.jim.devicecountdowntimer

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.jim.devicecountdowntimer.presentation.timer.TimerScreen
import com.jim.devicecountdowntimer.presentation.timer.TimerScreenState
import com.jim.devicecountdowntimer.presentation.timer.TimerStatus
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class CountDownTimerUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun countdownActiveStatus() {
        val timeRemaining = "12:00:00"
        val backgroundColor = Color(0xFF9BCF53)
        val tag = "timer_card"

        val mockHealthyStatus = TimerScreenState(
            timeRemaining = timeRemaining,
            timerStatus = TimerStatus.ACTIVE
        )
        composeTestRule.setContent {
            MaterialTheme {
                TimerScreen(state = mockHealthyStatus)
            }
        }
        composeTestRule.onNodeWithText(timeRemaining).assertIsDisplayed()
        composeTestRule.onNodeWithText(TimerStatus.ACTIVE.message).assertIsDisplayed()
        composeTestRule.onNodeWithText(TimerStatus.ACTIVE.statusMessage).assertIsDisplayed()
        composeTestRule.onNodeWithTag(tag).assertBackgroundColor(backgroundColor)

    }

    @Test
    fun countdownWarningStatus() {
        val timeRemaining = "01:00:00"

        val mockHealthyStatus = TimerScreenState(
            timeRemaining = timeRemaining,
            timerStatus = TimerStatus.WARNING
        )
        composeTestRule.setContent {
            MaterialTheme {
                TimerScreen(state = mockHealthyStatus)
            }
        }
        composeTestRule.onNodeWithText(timeRemaining).assertIsDisplayed()
        composeTestRule.onNodeWithText(TimerStatus.WARNING.message).assertIsDisplayed()
        composeTestRule.onNodeWithText(TimerStatus.WARNING.statusMessage).assertIsDisplayed()

    }

    @Test
    fun countdownLockedStatus() {
        val timeRemaining = "00:00:00"

        val mockHealthyStatus = TimerScreenState(
            timeRemaining = timeRemaining,
            timerStatus = TimerStatus.LOCKED
        )
        composeTestRule.setContent {
            MaterialTheme {
                TimerScreen(state = mockHealthyStatus)
            }
        }
        composeTestRule.onNodeWithText(timeRemaining).assertIsDisplayed()
        composeTestRule.onNodeWithText(TimerStatus.LOCKED.message).assertIsDisplayed()
        composeTestRule.onNodeWithText(TimerStatus.LOCKED.statusMessage).assertIsDisplayed()
    }

    private fun SemanticsNodeInteraction.assertBackgroundColor(expectedBackground: Color) {
        val capturedName = captureToImage().colorSpace.name
        assertEquals(expectedBackground.colorSpace.name, capturedName)
    }

}