package com.salihakbas.weatherapp.server

import com.salihakbas.weatherapp.model.CurrentResponseApi
import com.salihakbas.weatherapp.model.ForecastResponseApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") ApiKey: String,

    ): Call<CurrentResponseApi>


    @GET("data/2.5/weather")
    fun getForecastWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") ApiKey: String,

        ): Call<ForecastResponseApi>
}