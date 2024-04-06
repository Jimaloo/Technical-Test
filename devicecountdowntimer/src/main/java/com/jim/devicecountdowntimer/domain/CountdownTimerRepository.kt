package com.jim.devicecountdowntimer.domain

import android.annotation.SuppressLint
import com.jim.countdowntimer.data.ActiveUsagePeriodRemoteDataSource
import com.jim.countdowntimer.data.CountryIsoDataSource
import com.jim.countdowntimer.data.model.CountryIsoCode
import com.jim.devicecountdowntimer.data.ActiveUsagePeriodLocalDataSource
import com.jim.devicecountdowntimer.data.DeviceTimeDataSource
import com.jim.devicecountdowntimer.presentation.timer.TimerScreenState
import com.jim.devicecountdowntimer.presentation.timer.TimerStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class CountdownTimerRepository(
    val countryIsoDateSource: CountryIsoDataSource,
    val dispatchers: CoroutineDispatcher,
    val countryDeviceTimeDataSource: DeviceTimeDataSource,
    val activeUsagePeriodRemoteDataSource: ActiveUsagePeriodRemoteDataSource,
    val activeUsagePeriodLocalDataSource: ActiveUsagePeriodLocalDataSource,
    val timerFormatter: DateTimeFormatter
) {

    fun getTimerState(): Flow<TimerScreenState> = flow {
        while (true) {
            val deviceLockingTime = getDeviceLockingTime().truncatedTo(ChronoUnit.SECONDS)
            val countryISO = getCountryIso()
            val warningTimeForCountry = getWarningTimeForCountry(deviceLockingTime, countryISO)
            val currentDeviceTime = getDeviceTime().truncatedTo(ChronoUnit.SECONDS)
            val timerStatus = getTimerStatusForCountry(
                deviceLockingTime,
                currentDeviceTime,
                warningTimeForCountry
            )
            val timeRemaining = getFormattedDeviceTime(
                currentDeviceTime,
                deviceLockingTime
            )
            emit(
                TimerScreenState(
                    timeRemaining = timeRemaining,
                    timerStatus = timerStatus
                )
            )
        }
    }

    private suspend fun getCountryIso(): CountryIsoCode {
        return countryIsoDateSource.getCountryIsoCode()
    }

    private fun getDeviceTime(): OffsetDateTime {
        return countryDeviceTimeDataSource.getCurrentDeviceTime()
    }

    private suspend fun getDeviceLockingTime(): OffsetDateTime = withContext(dispatchers) {

        return@withContext if (activeUsagePeriodLocalDataSource.getLockingInfo() != null) {
            println("local DB call")
            activeUsagePeriodLocalDataSource.getLockingInfo()!!.lockTime // TODO: Could crash, refactor
        } else {
            println("network call")
            val lockingInfo = activeUsagePeriodRemoteDataSource.getLockingInfo()
            activeUsagePeriodLocalDataSource.saveLockingInfo(lockingInfo)
            lockingInfo.lockTime
        }
    }

    private fun getTimerStatusForCountry(
        deviceLockingTime: OffsetDateTime,
        currentTime: OffsetDateTime,
        countryWarningTime: OffsetDateTime
    ): TimerStatus {
        return when {
            currentTime.isBefore(countryWarningTime) -> TimerStatus.ACTIVE
            currentTime.isAfter(deviceLockingTime) -> TimerStatus.LOCKED
            else -> TimerStatus.WARNING
        }
    }

    private fun getWarningTimeForCountry(
        currentTime: OffsetDateTime,
        countryISO: CountryIsoCode
    ): OffsetDateTime {
        return when (countryISO) {
            CountryIsoCode.KE -> currentTime.minusHours(2L)
            CountryIsoCode.UG -> currentTime.minusHours(3L)
        }
    }

    @SuppressLint("DefaultLocale")
    private fun getFormattedDeviceTime(
        currentTime: OffsetDateTime,
        deviceLockingTime: OffsetDateTime
    ): String {
        val timeRemaining = currentTime.until(deviceLockingTime, ChronoUnit.SECONDS)
        return if (timeRemaining < 0) {
            "00:00:00 "
        } else {
            val hours = timeRemaining / 3600
            val minutes = (timeRemaining % 3600) / 60
            val seconds = timeRemaining % 60
            String.format("%02d:%02d:%02d", hours, minutes, seconds) // Hh:Mm:Ss

        }
    }


}