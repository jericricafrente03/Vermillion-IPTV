package com.bittelasia.vermillion.domain.usecases.post_stat

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.post_stat.InputStatistics
import com.bittelasia.vermillion.domain.model.post_stat.StatResponse
import kotlinx.coroutines.flow.Flow

class PostStatistics(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(inputStatistics: InputStatistics): Flow<DataState<StatResponse>> {
        return repository.postStatistics(inputStatistics)
    }
}