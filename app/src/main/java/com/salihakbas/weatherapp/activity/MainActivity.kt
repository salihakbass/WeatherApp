package com.salihakbas.weatherapp.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.github.matteobattilana.weather.PrecipType
import com.salihakbas.weatherapp.R
import com.salihakbas.weatherapp.databinding.ActivityMainBinding
import com.salihakbas.weatherapp.model.CurrentResponseApi
import com.salihakbas.weatherapp.viewmodel.WeatherViewModel
import retrofit2.Call
import retrofit2.Response
import java.util.Calendar
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val calendar by lazy { Calendar.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }

        binding.apply {
            var lat = 41.002697
            var lon = 39.716763
            var name = "Trabzon"

            cityTxt.text = name

            weatherViewModel.loadCurrentWeather(lat, lon, "metric").enqueue(object :
                retrofit2.Callback<CurrentResponseApi> {
                override fun onResponse(
                    call: Call<CurrentResponseApi>,
                    response: Response<CurrentResponseApi>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        detailLayout.visibility = View.VISIBLE
                        data?.let {
                            statusTxt.text = it.weather?.get(0)?.main ?: "-"
                            windTxt.text = it.wind?.speed?.let { Math.round(it).toString() } + "Km"
                            humidityTxt.text = it.main?.humidity?.toString()+"%"
                            currentTempTxt.text = it.main?.temp?.let { Math.round(it).toString() }
                            maxTempTxt.text = it.main?.tempMax?.let { Math.round(it).toString() }
                            minTempTxt.text = it.main?.tempMin?.let { Math.round(it).toString() }

                            val drawable = if (isNightNow()) {
                                R.drawable.night_bg
                            } else {
                                setDynamicallyWallpaper(it.weather?.get(0)?.icon ?: "-")
                            }
                            bgImage.setImageResource(drawable)
                            setEffectRainSnow(it.weather?.get(0)?.icon ?: "-")
                        }
                    }
                }

                override fun onFailure(call: Call<CurrentResponseApi>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_SHORT).show()
                }

            })
        }

    }

    private fun isNightNow(): Boolean {
        return calendar.get(Calendar.HOUR_OF_DAY) >= 18
    }

    private fun setDynamicallyWallpaper(icon: String): Int {
        return when (icon.dropLast(1)) {
            "01" -> {
                initWeatherView(PrecipType.CLEAR)
                R.drawable.snow_bg
            }

            "02", "03", "04" -> {
                initWeatherView(PrecipType.CLEAR)
                R.drawable.cloudy_bg
            }

            "09", "10", "11" -> {
                initWeatherView(PrecipType.RAIN)
                R.drawable.rainy_bg
            }

            "13" -> {
                initWeatherView(PrecipType.SNOW)
                R.drawable.snow_bg
            }

            "50" -> {
                initWeatherView(PrecipType.CLEAR)
                R.drawable.haze_bg
            }

            else -> 0
        }
    }

    private fun setEffectRainSnow(icon: String) {
        when (icon.dropLast(1)) {
            "01" -> {
                initWeatherView(PrecipType.CLEAR)

            }

            "02", "03", "04" -> {
                initWeatherView(PrecipType.CLEAR)

            }

            "09", "10", "11" -> {
                initWeatherView(PrecipType.RAIN)

            }

            "13" -> {
                initWeatherView(PrecipType.SNOW)

            }

            "50" -> {
                initWeatherView(PrecipType.CLEAR)

            }


        }
    }

    private fun initWeatherView(type: PrecipType) {
        binding.weatherView.apply {
            setWeatherData(type)
            angle = -20
            emissionRate = 100.0f
        }
    }
}