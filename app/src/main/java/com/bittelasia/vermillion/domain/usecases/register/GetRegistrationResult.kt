package com.bittelasia.vermillion.domain.usecases.register

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.result.Register
import com.bittelasia.vermillion.domain.model.stb.StbRegistration
import kotlinx.coroutines.flow.Flow

class GetRegistrationResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(user: StbRegistration): Flow<DataState<Register>> {
        return repository.registerResult(user)
    }
}