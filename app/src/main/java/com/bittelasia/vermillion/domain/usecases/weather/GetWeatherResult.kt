package com.bittelasia.vermillion.domain.usecases.weather

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.weather.item.GetWeeklyWeatherForecastData
import kotlinx.coroutines.flow.Flow

class GetWeatherResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<GetWeeklyWeatherForecastData>>> {
        return repository.weatherResult()
    }
}