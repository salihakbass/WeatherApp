package com.salihakbas.weatherapp.repository

import com.salihakbas.weatherapp.server.ApiServices

class WeatherRepository(val api: ApiServices) {

    fun getCurrentWeather(lat:Double,lng:Double,unit:String) =
        api.getCurrentWeather(lat,lng,unit,"e7cee661dd30ea60b6ce1782353360da")

    fun getForecastWeather(lat:Double,lng:Double,unit:String) =
        api.getForecastWeather(lat,lng,unit,"e7cee661dd30ea60b6ce1782353360da")
}