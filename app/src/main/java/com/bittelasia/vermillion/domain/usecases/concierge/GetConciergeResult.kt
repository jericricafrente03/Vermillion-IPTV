package com.bittelasia.vermillion.domain.usecases.concierge

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.concierge.item.ConciergeData
import kotlinx.coroutines.flow.Flow

class GetConciergeResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<ConciergeData>>> {
        return repository.conciergeResult()
    }
}