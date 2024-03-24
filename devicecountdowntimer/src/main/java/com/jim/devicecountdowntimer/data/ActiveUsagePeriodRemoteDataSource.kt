package com.jim.countdowntimer.data

import com.jim.countdowntimer.data.model.ActiveUsagePeriod
import kotlinx.coroutines.delay
import java.time.OffsetDateTime

class ActiveUsagePeriodRemoteDataSource {

    suspend fun getLockingInfo(): ActiveUsagePeriod {
        delay(500) // Simulating network calls
        return ActiveUsagePeriod(
            OffsetDateTime.now().withHour(23).withMinute(0).withSecond(0)
        )
    }
}