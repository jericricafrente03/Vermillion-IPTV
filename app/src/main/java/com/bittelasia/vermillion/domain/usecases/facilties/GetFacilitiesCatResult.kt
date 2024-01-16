package com.bittelasia.vermillion.domain.usecases.facilties

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.facilities.category.FacilitiesCategoryData
import kotlinx.coroutines.flow.Flow

class GetFacilitiesCatResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<FacilitiesCategoryData>>> {
        return repository.facilitiesCategoryResult()
    }
}