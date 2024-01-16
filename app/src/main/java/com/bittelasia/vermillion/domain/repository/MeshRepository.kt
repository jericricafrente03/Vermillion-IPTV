package com.bittelasia.vermillion.domain.repository

import android.content.Context
import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.domain.model.license.response.LicenseDate
import com.bittelasia.vermillion.domain.model.result.Register
import com.bittelasia.vermillion.domain.model.stb.StbRegistration
import kotlinx.coroutines.flow.Flow

interface MeshRepository {
    fun registerResult(stbRegistration: StbRegistration): Flow<DataState<Register>>
    suspend fun postResult(app: Context): Flow<DataState<LicenseDate>>
}