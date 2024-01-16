package com.bittelasia.vermillion.domain.usecases.license

import android.content.Context
import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.license.response.LicenseDate
import kotlinx.coroutines.flow.Flow

class GetLicenseResult(
    private val repository: MeshListApiImpl
) {
    suspend operator fun invoke(context: Context): Flow<DataState<LicenseDate>> {
        return repository.postResult(context)
    }
}