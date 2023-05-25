package com.example.myweatherapp.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.myweatherapp.data.WeatherData
import com.example.myweatherapp.data.WeatherUiState
import com.example.myweatherapp.key.BASE_KEY
import com.example.myweatherapp.network.WeatherAPI
import kotlinx.coroutines.launch

@Composable
fun MainMenu(weatherViewModel: ViewModelScreen = viewModel()) {

    val weatherUiState by weatherViewModel.uiState.collectAsState()

    val weather = weatherUiState.curWeather

    if (weather != null) {
        Background(weather, weatherViewModel)
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Background(weather: WeatherData?, weatherViewModel: ViewModelScreen) {

    var text by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    var str by remember {
        mutableStateOf(weather!!.current.condition.icon)
    }

    var country by remember {
         mutableStateOf(weather!!.location.country)
    }

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

        Text("Weather info: ", fontWeight = FontWeight.Bold)
        Text("Country: $country")
        Text("Town: ${weather!!.location.name}")
        Text("Condition: ${weather.current.condition.text}")
        Text("Temperature: ${weather.current.temp_c}")

        AsyncImage(model = "https:$str", contentDescription = "Image", modifier = Modifier.size(100.dp,100.dp))

        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(top = 100.dp)) {

            TextField(value = text,
                onValueChange = {newText -> text = newText},
                maxLines = 1,
                shape = RectangleShape,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()})
            )
            Button(onClick = {
            weatherViewModel.viewModelScope.launch {
                var state: WeatherUiState? = null
                try {
                    val result = WeatherAPI.retrofitService.getWeatherInfo(BASE_KEY, text.text, "1", "no", "no")
                    state = WeatherUiState(result)
                    country = state.curWeather!!.location.country
                    weather!!.location.name = state.curWeather!!.location.name
                    weather.current.condition.text = state.curWeather!!.current.condition.text
                    weather.current.temp_c = state.curWeather!!.current.temp_c
                    str = state.curWeather!!.current.condition.icon

                } catch (e: Exception) {
                    Toast.makeText(context, "Town not found!", Toast.LENGTH_SHORT).show()
                }

            }
            }) {
                Text(text = "Show Me!")
            }
        }

    }

}
