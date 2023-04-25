package com.example.myweatherapp.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.data.WeatherUiState
import com.example.myweatherapp.network.WeatherAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModelScreen: ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState(curWeather = null))

    val uiState : StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        getWeatherInfo()
    }

    private fun getWeatherInfo() {
        viewModelScope.launch {
            try {
                val listResult = WeatherAPI.retrofitService.getWeatherInfo("36d7cb024f314771be4113602231304","Athens", "1", "no", "no")
                _uiState.value = WeatherUiState(listResult)
                println("The result is: ${listResult.current}")
            } catch (e: Exception) {
                println("ERROR -- >  ${e.message}")
            }
        }
    }

}