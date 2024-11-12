package com.assessment.android.ui.content.main

import com.assessment.android.data.model.WeatherResponse

sealed class ResultState {
    object Loading : ResultState()
    data class Success(val data: WeatherResponse) : ResultState()
    data class Error(val message: String) : ResultState()
}