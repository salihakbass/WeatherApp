package com.salihakbas.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import com.salihakbas.weatherapp.repository.WeatherRepository
import com.salihakbas.weatherapp.server.ApiClient
import com.salihakbas.weatherapp.server.ApiServices

class WeatherViewModel(val repository: WeatherRepository) : ViewModel() {

    constructor() : this(WeatherRepository(ApiClient().getClient().create(ApiServices::class.java)))

    fun loadCurrentWeather(lat: Double,lng: Double,unit: String) =
        repository.getCurrentWeather(lat,lng, unit)

    fun loadForecastWeather(lat: Double,lng: Double,unit: String) =
        repository.getForecastWeather(lat,lng, unit)
}