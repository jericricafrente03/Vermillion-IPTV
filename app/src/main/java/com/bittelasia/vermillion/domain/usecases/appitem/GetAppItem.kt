package com.bittelasia.vermillion.domain.usecases.appitem

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.app_item.item.AppData
import kotlinx.coroutines.flow.Flow

class GetAppItem(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<AppData>>> {
        return repository.uiIconResult()
    }
}