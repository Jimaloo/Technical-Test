package com.jim.countdowntimer.data

import com.jim.countdowntimer.data.model.CountryIsoCode

class CountryIsoDataSource {
    suspend fun getCountryIsoCode(): CountryIsoCode{
        return CountryIsoCode.UG
    }
}