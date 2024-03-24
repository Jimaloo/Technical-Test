package com.jim.countdowntimer.data.model

import java.time.OffsetDateTime
import java.time.OffsetTime

data class ActiveUsagePeriod(
    val lockTime: OffsetDateTime
)