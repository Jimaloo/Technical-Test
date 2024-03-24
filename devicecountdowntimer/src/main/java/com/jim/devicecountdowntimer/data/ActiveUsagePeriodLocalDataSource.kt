package com.jim.devicecountdowntimer.data

import com.jim.countdowntimer.data.model.ActiveUsagePeriod
import kotlinx.coroutines.delay
import java.time.OffsetDateTime

class ActiveUsagePeriodLocalDataSource {

    var activeUsagePeriod: ActiveUsagePeriod? = null

    suspend fun getLockingInfo(): ActiveUsagePeriod? {
        delay(500) // Simulating local DB calls
        return activeUsagePeriod
    }

    fun saveLockingInfo(lockingInfo: ActiveUsagePeriod) {
        activeUsagePeriod = lockingInfo
    }

}