package com.bittelasia.vermillion.domain.usecases.broadcast

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.broadcast.BroadCastData
import kotlinx.coroutines.flow.Flow

class GetBroadcastResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<BroadCastData>> {
        return repository.broadcast()
    }
}