package com.jim.devicecountdowntimer.presentation

import androidx.compose.ui.graphics.Color

enum class TimerStatus(
    val timerBackgroundColor: Color,
    val timerBorderColor: Color,
    val message: String = ""
) {
    HEALTHY(Color(0xFF9BCF53), Color(0xFF416D19),"Device status active"),
    WARNING(Color(0xFFFDA403),Color(0xFFE8751A) ,"WARNING: Payment is due soon. Kindly pay to avoid disconnection"),
    LOCKED(Color(0xFFBB2525), Color(0xFF952323),"LOCKED: This device is locked due to payment")
}