package com.jim.devicecountdowntimer.data

import com.jim.countdowntimer.data.model.ActiveUsagePeriod
import kotlinx.coroutines.delay
import java.time.OffsetDateTime

class  DeviceTimeDataSource {

    fun getCurrentDeviceTime() : OffsetDateTime {
        return OffsetDateTime.now()
    }

    suspend fun getCurrentDateTime(): ActiveUsagePeriod {
        delay(500) // simulate network call instead of relying on device time
        return ActiveUsagePeriod(
            OffsetDateTime.now()
        )
    }
}