package com.bittelasia.vermillion.domain.usecases.guest

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.guest.item.GuestData
import kotlinx.coroutines.flow.Flow

class GetGuestResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<GuestData>> {
        return repository.guestResult()
    }
}