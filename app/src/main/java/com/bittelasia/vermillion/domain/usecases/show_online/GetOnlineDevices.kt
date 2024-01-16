package com.bittelasia.vermillion.domain.usecases.show_online

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import kotlinx.coroutines.flow.Flow

class GetOnlineDevices(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<String>> {
        return repository.showOnline()
    }
}