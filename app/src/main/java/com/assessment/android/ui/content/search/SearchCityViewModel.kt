package com.assessment.android.ui.content.search

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.android.data.repository.WeatherRepository
import com.assessment.android.ui.content.main.ResultState
import com.assessment.android.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository, @ApplicationContext private val context: Context
) : ViewModel() {

    val weatherData: MutableState<ResultState?> = mutableStateOf(null)
    val lastSearchedCity: MutableState<String> = mutableStateOf("")
    init {
        loadLastSearchedCity()
    }

    fun getWeatherData(cityName: String) = viewModelScope.launch {
        lastSearchedCity.value = cityName
        saveLastSearchedCity(cityName)

        when (val result = weatherRepository.getWeather(cityName)) {
            is NetworkResult.Loading -> {
                weatherData.value = ResultState.Loading

            }
            is NetworkResult.Success -> {
                result.data?.let {
                    weatherData.value = ResultState.Success(result.data)
                }
            }
            is NetworkResult.Error -> {
                if (result.message == "404") {
                    weatherData.value = ResultState.Error("City Not Found")
                } else {
                    weatherData.value = ResultState.Error("Error Occurred, Please try again later!")
                }
            }
        }
    }

    private fun saveLastSearchedCity(cityName: String) {
        val sharedPref = context.getSharedPreferences("WeatherApp", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("last_searched_city", cityName)
            apply()
        }
    }


    private fun loadLastSearchedCity() {
        val sharedPref = context.getSharedPreferences("WeatherApp", Context.MODE_PRIVATE)
        val lastCity = sharedPref.getString("last_searched_city", "")
        if (!lastCity.isNullOrEmpty()) {
            lastSearchedCity.value = lastCity
        }
    }

}
