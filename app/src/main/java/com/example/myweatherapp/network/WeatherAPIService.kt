package com.example.myweatherapp.network

import com.example.myweatherapp.data.WeatherData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.weatherapi.com"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WeatherAPIService {

    @GET("/v1/forecast.json?key=36d7cb024f314771be4113602231304&q=town&days=1&aqi=no&alerts=no")
    suspend fun getWeatherInfo(@Query("town") town : String = "Athens"): WeatherData
}

object WeatherAPI {
    val retrofitService : WeatherAPIService by lazy {
        retrofit.create(WeatherAPIService::class.java)
    }
}
