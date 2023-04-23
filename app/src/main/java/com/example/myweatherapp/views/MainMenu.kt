package com.example.myweatherapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.myweatherapp.data.WeatherData

@Composable
fun MainMenu(weatherViewModel: ViewModelScreen = viewModel()) {

    val weatherUiState by weatherViewModel.uiState.collectAsState()

    val weather = weatherUiState.curWeather


    //Text("Hello")

    if (weather != null) {
        Background(weather)
    }


}

@Composable
fun Background(weather: WeatherData?) {

    Column(modifier = Modifier.fillMaxSize()) {
        Text("The weather is: ${weather!!.current.condition.text} ")

        var str = weather.current!!.condition.icon
        str = "https:" + str
        println("The text is: ${str}")

        AsyncImage(model = str, contentDescription = "Image")

    }

}
