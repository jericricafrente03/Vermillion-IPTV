package com.bittelasia.vermillion.domain.usecases.themes

import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.model.theme.item.Theme
import kotlinx.coroutines.flow.Flow

class GetThemesBGResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<Theme>> {
        return repository.themesBgResult()
    }
}