package com.bittelasia.vermillion.domain.usecases.time

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.time.Time
import kotlinx.coroutines.flow.Flow

class GetTimeResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<Time>> {
        return repository.time()
    }
}