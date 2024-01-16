package com.bittelasia.vermillion.domain.usecases.message

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.message.readmessage.ReadMessage
import kotlinx.coroutines.flow.Flow

class GetReadMessageResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(id: String): Flow<DataState<ReadMessage>> {
        return repository.readMessageResult(id = id)
    }
}