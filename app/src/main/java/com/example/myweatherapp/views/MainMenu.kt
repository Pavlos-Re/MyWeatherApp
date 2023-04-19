package com.example.myweatherapp.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainMenu(weatherViewModel: ViewModelScreen = viewModel()) {

    val weatherUiState by weatherViewModel.uiState.collectAsState()

    val weather = weatherUiState.curWeather

    if (weather != null) {
        println("The weather is: ${weather.current.condition.text} ")
    }


}