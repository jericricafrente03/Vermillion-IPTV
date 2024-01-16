package com.bittelasia.vermillion.domain.usecases.channel

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.channel.item.ChannelData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetChannelResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<ChannelData>>> {
        return repository.channelResult()
    }
}