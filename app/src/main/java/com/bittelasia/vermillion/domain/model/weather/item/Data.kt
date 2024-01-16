package com.bittelasia.vermillion.domain.model.weather.item

data class Data(
    val date: String,
    val description: String,
    val dew_point: String,
    val humidity: String,
    val icon: String,
    val id: String,
    val pressure: String,
    val sunrise: String,
    val sunset: String,
    val temp_day: String,
    val temp_eve: String,
    val temp_max: String,
    val temp_min: String,
    val temp_morn: String,
    val temp_night: String,
    val wind_speed: String
)