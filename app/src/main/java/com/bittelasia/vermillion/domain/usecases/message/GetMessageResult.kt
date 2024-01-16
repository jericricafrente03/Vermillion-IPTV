package com.bittelasia.vermillion.domain.usecases.message

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.message.item.GetMessageData
import kotlinx.coroutines.flow.Flow

class GetMessageResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<GetMessageData>>> {
        return repository.messageResult()
    }
}