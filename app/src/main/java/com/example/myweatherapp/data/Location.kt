package com.example.myweatherapp.data

data class Location(
    var country: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Int,
    val lon: Double,
    var name: String,
    val region: String,
    val tz_id: String
)