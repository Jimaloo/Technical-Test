package com.jim.countdowntimer.data

import com.jim.countdowntimer.data.model.ActiveUsagePeriod
import kotlinx.coroutines.delay
import java.time.OffsetDateTime

class ActiveUsagePeriodRemoteDataSource {

    suspend fun getLockingInfo(): ActiveUsagePeriod {
        delay(5000) // Simulating network calls
        return ActiveUsagePeriod(
            OffsetDateTime.now().withHour(10).withMinute(16).withSecond(0)
        )
    }
}