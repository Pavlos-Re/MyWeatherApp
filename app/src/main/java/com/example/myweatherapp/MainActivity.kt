package com.example.myweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme
import com.example.myweatherapp.views.MainMenu

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MyApp {
                MainMenu()
            }

        }
    }
}


@Composable
fun MyApp(content: @Composable () -> Unit) {
    MyWeatherAppTheme() {

        content()

    }
}