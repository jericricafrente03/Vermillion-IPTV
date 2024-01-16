package com.bittelasia.vermillion.domain.usecases.config

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.config.item.SystemData
import kotlinx.coroutines.flow.Flow

class GetConfigResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<SystemData>> {
        return repository.configResult()
    }
}