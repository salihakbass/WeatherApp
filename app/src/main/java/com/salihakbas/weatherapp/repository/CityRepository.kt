package com.salihakbas.weatherapp.repository

import com.salihakbas.weatherapp.server.ApiServices

class CityRepository(val api: ApiServices) {
    fun getCities(q: String, limit: Int) =
        api.getCitiesList(q, limit, "e7cee661dd30ea60b6ce1782353360da")
}