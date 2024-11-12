package com.assessment.android.ui.content.main

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.android.data.location.LocationTracker
import com.assessment.android.data.repository.WeatherRepository
import com.assessment.android.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val currentLocationWeatherData: MutableState<ResultState?> = mutableStateOf(null)

    private fun getCurrentWeatherData(latitude: Double, longitude: Double) = viewModelScope.launch {

        when (val result = weatherRepository.getWeatherByLocation(latitude, longitude)) {
            is NetworkResult.Loading -> {
                currentLocationWeatherData.value = ResultState.Loading

            }
            is NetworkResult.Success -> {
                result.data?.let {
                    currentLocationWeatherData.value = ResultState.Success(result.data)
                }
            }
            is NetworkResult.Error -> {
                currentLocationWeatherData.value = ResultState.Error("Something went wrong")
            }
        }
    }

    private var currentLocation by mutableStateOf<Location?>(null)
    fun getCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            currentLocation = locationTracker.getCurrentLocation()
            currentLocation?.let {
                getCurrentWeatherData(it.latitude, it.longitude)

            }
        }
    }


}
