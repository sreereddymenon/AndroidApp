package com.assessment.android.ui.content.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.assessment.android.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.assessment.android.ui.content.main.WeatherContentScreen

@Composable
fun SearchContentScreen(onMain: () -> Unit, viewModel: SearchCityViewModel = hiltViewModel()) {

    val lastSearchedCity = viewModel.lastSearchedCity.value

    val cityName: MutableState<String> = remember { mutableStateOf(lastSearchedCity) }

    val result = viewModel.weatherData.value

    val scrollStateScreen = rememberScrollState()


    Surface(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollStateScreen)
    ) {
        Image(
            painter = painterResource(R.drawable.images),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = cityName.value,
                    onValueChange = {
                        cityName.value = it
                        viewModel.getWeatherData(cityName.value)
                        Log.e("City", cityName.value)
                    },
                    enabled = true,
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp))
                    },
                    label = { Text(text = "Search here...")},
                    modifier = Modifier.fillMaxWidth()
                )
                WeatherContentScreen(result)
            }

        }

    }


}
