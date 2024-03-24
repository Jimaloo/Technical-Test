package com.jim.devicecountdowntimer.di

import com.jim.countdowntimer.data.ActiveUsagePeriodRemoteDataSource
import com.jim.countdowntimer.data.CountryIsoDataSource
import com.jim.devicecountdowntimer.data.ActiveUsagePeriodLocalDataSource
import com.jim.devicecountdowntimer.domain.CountdownTimerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TimerHiltModules {

    @Provides
    fun provideCountdownTimerRepository(
        activeUsagePeriodRemoteDataSource: ActiveUsagePeriodRemoteDataSource,
        activeUsagePeriodLocalDataSource: ActiveUsagePeriodLocalDataSource,
        countryIsoCodeDataSource: CountryIsoDataSource,
        dispatchers: CoroutineDispatcher
    ): CountdownTimerRepository {
        return CountdownTimerRepository(
            activeUsagePeriodRemoteDataSource = activeUsagePeriodRemoteDataSource,
            activeUsagePeriodLocalDataSource = activeUsagePeriodLocalDataSource,
            countryIsoDateSource = countryIsoCodeDataSource,
            dispatchers = dispatchers
        )
    }

    @Provides
    fun provideActiveUsagePeriodRemoteDataSource(): ActiveUsagePeriodRemoteDataSource {
        return ActiveUsagePeriodRemoteDataSource()
    }

    @Provides
    fun provideActiveUsagePeriodLocalDataSource(): ActiveUsagePeriodLocalDataSource {
        return ActiveUsagePeriodLocalDataSource()
    }

    @Provides
    fun provideCountryIsoCodeDataSource(): CountryIsoDataSource {
        return CountryIsoDataSource()
    }


    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideTimerFormatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("Hh:Mm:Ss")
    }
}