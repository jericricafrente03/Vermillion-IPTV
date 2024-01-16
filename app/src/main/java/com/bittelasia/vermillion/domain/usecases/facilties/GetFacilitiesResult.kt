package com.bittelasia.vermillion.domain.usecases.facilties

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.facilities.item.FacilitiesData
import kotlinx.coroutines.flow.Flow

class GetFacilitiesResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<FacilitiesData>>> {
        return repository.facilitiesResult()
    }
}