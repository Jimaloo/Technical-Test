package com.jim.devicecountdowntimer.domain

import com.jim.countdowntimer.data.ActiveUsagePeriodRemoteDataSource
import com.jim.countdowntimer.data.CountryIsoDataSource
import com.jim.countdowntimer.data.model.ActiveUsagePeriod
import com.jim.countdowntimer.data.model.CountryIsoCode
import com.jim.devicecountdowntimer.data.ActiveUsagePeriodLocalDataSource
import com.jim.devicecountdowntimer.data.DeviceTimeDataSource
import com.jim.devicecountdowntimer.presentation.timer.TimerScreenState
import com.jim.devicecountdowntimer.presentation.timer.TimerStatus
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class CountdownTimerRepositoryTest {

    @MockK
    private lateinit var activeUsagePeriodRemoteDataSource: ActiveUsagePeriodRemoteDataSource

    @MockK
    private lateinit var activeUsagePeriodLocalDataSource: ActiveUsagePeriodLocalDataSource

    @MockK
    private lateinit var countryIsoDataSource: CountryIsoDataSource
    @MockK
    private lateinit var deviceTimeDataSource: DeviceTimeDataSource
    @MockK
    private lateinit var deviceTimeFormatter: DateTimeFormatter

    private lateinit var repository: CountdownTimerRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        repository = CountdownTimerRepository(
            countryIsoDataSource,
            testDispatcher,
            deviceTimeDataSource,
            activeUsagePeriodRemoteDataSource,
            activeUsagePeriodLocalDataSource,
            deviceTimeFormatter
        )
    }

    @Test
    fun `repository should fetch data from remote source when no data is cached`() =
        runTest(testDispatcher) {
            coEvery { activeUsagePeriodLocalDataSource.getLockingInfo() } returns null
            justRun { activeUsagePeriodLocalDataSource.saveLockingInfo(any()) }
            coEvery { activeUsagePeriodRemoteDataSource.getLockingInfo() } returns mockk(relaxed = true)
            every { deviceTimeDataSource.getCurrentDeviceTime() } returns mockk(relaxed = true)
            repository.getTimerState().first()
            advanceUntilIdle()
            coVerify(exactly = 1) { activeUsagePeriodRemoteDataSource.getLockingInfo() }
            coVerify(exactly = 1) { activeUsagePeriodLocalDataSource.saveLockingInfo(any()) }
        }

    @Test
    fun `repository should fetch data from local source when data is cached`() =
        runTest(testDispatcher) {
            coEvery { activeUsagePeriodLocalDataSource.getLockingInfo() } returns mockk(relaxed = true)
            justRun { activeUsagePeriodLocalDataSource.saveLockingInfo(any()) }

             every { deviceTimeDataSource.getCurrentDeviceTime() } returns mockk(relaxed = true)

            repository.getTimerState().first()

            coVerify(inverse = true) { activeUsagePeriodRemoteDataSource.getLockingInfo() }
            coVerify(inverse = true) { activeUsagePeriodLocalDataSource.saveLockingInfo(any()) }
        }

    @Test
    fun `generate an active status`() =
        runTest(testDispatcher) {
            val mockDeviceLockingTime =
                OffsetDateTime.of(2024, 3, 24, 23, 59, 59, 0, OffsetDateTime.now().offset)
            val mockLockingInfo = mockk<ActiveUsagePeriod>()
            every { mockLockingInfo.lockTime } returns mockDeviceLockingTime

            val mockCurrentDeviceTime =
                OffsetDateTime.of(2024, 3, 24, 21, 0, 0, 0, OffsetDateTime.now().offset)

            every { deviceTimeDataSource.getCurrentDeviceTime()  } returns mockCurrentDeviceTime

            val expectedResult = TimerScreenState(
                timeRemaining = "02:59:59",
                timerStatus = TimerStatus.WARNING
            )

            coEvery { activeUsagePeriodLocalDataSource.getLockingInfo() } returns mockLockingInfo
            val actualResult = repository.getTimerState().first()

            assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `generate an warning status for KE`() =
        runTest(testDispatcher) {
            val mockDeviceLockTime =
                OffsetDateTime.of(2024, 4, 24, 23, 59, 59, 0, OffsetDateTime.now().offset)
            val mockLockingInfo = mockk<ActiveUsagePeriod>()
            every { mockLockingInfo.lockTime } returns mockDeviceLockTime

            val mockCurrentDeviceTime =
                OffsetDateTime.of(2024, 4, 24, 21, 59, 59, 0, OffsetDateTime.now().offset)

             every { deviceTimeDataSource.getCurrentDeviceTime()  } returns mockCurrentDeviceTime

            val expectedResult = TimerScreenState(
                timeRemaining = "02:00:00",
                timerStatus = TimerStatus.WARNING
            )

            coEvery { activeUsagePeriodLocalDataSource.getLockingInfo() } returns mockLockingInfo

            coEvery { countryIsoDataSource.getCountryIsoCode() } returns CountryIsoCode.KE

            val actualResult = repository.getTimerState().first()

            assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `generate an warning status for UG`() =
        runTest(testDispatcher) {
            val mockDeviceLockTime =
                OffsetDateTime.of(2024, 4, 24, 23, 59, 59, 0, OffsetDateTime.now().offset)
            val mockLockingInfo = mockk<ActiveUsagePeriod>()
            every { mockLockingInfo.lockTime } returns mockDeviceLockTime

            val mockCurrentDeviceTime =
                OffsetDateTime.of(2024, 4, 24, 21, 59, 59, 0, OffsetDateTime.now().offset)

              every { deviceTimeDataSource.getCurrentDeviceTime()  } returns mockCurrentDeviceTime

            val expectedResult = TimerScreenState(
                timeRemaining = "02:00:00",
                timerStatus = TimerStatus.WARNING
            )

            coEvery { activeUsagePeriodLocalDataSource.getLockingInfo() } returns mockLockingInfo

            coEvery { countryIsoDataSource.getCountryIsoCode() } returns CountryIsoCode.UG

            val actualResult = repository.getTimerState().first()

            assertEquals(expectedResult, actualResult)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `generate an locked status`() =
        runTest(testDispatcher) {
            val mockDeviceLockTime =
                OffsetDateTime.of(2024, 3, 24, 23, 59, 58, 0, OffsetDateTime.now().offset)
            val mockLockingInfo = mockk<ActiveUsagePeriod>()
            every { mockLockingInfo.lockTime } returns mockDeviceLockTime

            val mockCurrentDeviceTime =
                OffsetDateTime.of(2024, 3, 24, 23, 59, 59, 0, OffsetDateTime.now().offset)

             every { deviceTimeDataSource.getCurrentDeviceTime()  } returns mockCurrentDeviceTime

            val expectedResult = TimerScreenState(
                timeRemaining = "00:00:00 ",
                timerStatus = TimerStatus.LOCKED
            )

            coEvery { countryIsoDataSource.getCountryIsoCode() } returns CountryIsoCode.UG

            coEvery { activeUsagePeriodLocalDataSource.getLockingInfo() } returns mockLockingInfo

            val actualResult = repository.getTimerState().first()
            advanceUntilIdle()
            assertEquals(expectedResult, actualResult)
        }
}