package com.assessment.android.ui.content.main

import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assessment.android.data.model.WeatherResponse
import com.assessment.android.utils.Constants
import com.assessment.android.utils.getFormattedDate
import com.assessment.android.utils.helpers.EpochConverter
import com.assessment.android.ui.theme.Blue75


@Composable
fun WeatherContentScreen(result: ResultState?) {
    when (result) {
        is ResultState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is ResultState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = result.message
                )
            }
        }
        is ResultState.Success -> {
            Column {
                TodayDateBox(result.data)
                MainContentItem(result.data)
                WeatherDetailSection(result.data)
            }
        }

        else -> {}
    }
}

@Composable
fun WeatherDetailSection(data: WeatherResponse) {
    CurrentWeatherDetailRow(
        title1 = "TEMP",
        value1 = "${data.main.temp}${Constants.DEGREE_VALUE}",
        title2 = "FEELS LIKE",
        value2 = "${data.main.feelsLike}${Constants.DEGREE_VALUE}"
    )
    CurrentWeatherDetailRow(
        title1 = "CLOUDINESS",
        value1 = "${data.clouds.all}%",
        title2 = "HUMIDITY",
        value2 = "${data.main.humidity}%"
    )
    CurrentWeatherDetailRow(
        title1 = "SUNRISE",
        value1 = "${EpochConverter.readTimestamp(data.sys.sunrise)}AM",
        title2 = "SUNSET",
        value2 = "${EpochConverter.readTimestamp(data.sys.sunset)}PM"
    )
    CurrentWeatherDetailRow(
        title1 = "WIND",
        value1 = "${data.wind.speed}KM",
        title2 = "PRESSURE",
        value2 = "${data.wind.deg}"
    )
}

@Composable
fun CurrentWeatherDetailRow(title1: String, value1: String, title2: String, value2: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        CurrentWeatherDetailCard(title = title1, value = value1)
        CurrentWeatherDetailCard(title = title2, value = value2)
    }
}


@Composable
private fun CurrentWeatherDetailCard(title: String, value: String) {
    Card(
        modifier = Modifier.size(150.dp),
        backgroundColor = Blue75,
        shape = MaterialTheme.shapes.small,
        border = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp),
            Alignment.TopStart
        ) {
            Text(
                text = title,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
            )
        }
        Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
            Text(
                text = value,
                color = Color.White,
                style = MaterialTheme.typography.h6
            )
        }
    }
}


@Composable
fun MainContentItem(weatherResponse: WeatherResponse) {
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Blue75,
        contentColor = Color.White,
        modifier = Modifier.padding(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 20.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {


            Text(
                text = weatherResponse.name,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "${weatherResponse.main.temp}${Constants.DEGREE_VALUE}",
                style = MaterialTheme.typography.h3
            )
            Text(
                text = weatherResponse.weather[0].description,
                style = MaterialTheme.typography.h6,
                color = Color.DarkGray
            )
            Text(
                text = "H:${weatherResponse.main.tempMax}°  L:${weatherResponse.main.tempMin}°",
                style = MaterialTheme.typography.h6
            )
        }
    }
}


@Composable
fun TodayDateBox(weatherResponse: WeatherResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            shape = RoundedCornerShape(32.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = getFormattedDate(weatherResponse.dt),
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(8.dp),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 19.sp
                )
            }
        }
    }
}
