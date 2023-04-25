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

private const val BASE_URL = "https://api.weatherapi.com/v1/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WeatherAPIService {
    //key=36d7cb024f314771be4113602231304&q
    @GET("forecast.json")
    suspend fun getWeatherInfo(
        @Query("key") key : String,
        @Query("q") town : String,
        @Query("days") days : String,
        @Query("aqi") aqi : String,
        @Query("alerts") alerts : String,
        ): WeatherData
}

object WeatherAPI {
    val retrofitService : WeatherAPIService by lazy {
        retrofit.create(WeatherAPIService::class.java)
    }
}
