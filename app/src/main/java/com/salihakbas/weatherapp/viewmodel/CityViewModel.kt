package com.salihakbas.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import com.salihakbas.weatherapp.repository.CityRepository
import com.salihakbas.weatherapp.server.ApiClient
import com.salihakbas.weatherapp.server.ApiServices

class CityViewModel(val repository: CityRepository) : ViewModel() {
    constructor() : this(CityRepository(ApiClient().getClient().create(ApiServices::class.java)))

    fun loadCity(q: String, limit: Int) =
        repository.getCities(q, limit)

}