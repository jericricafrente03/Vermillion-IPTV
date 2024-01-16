package com.bittelasia.vermillion.di

import com.bittelasia.vermillion.data.local.MeshDataBase
import com.bittelasia.vermillion.data.remote.MeshApi
import com.bittelasia.vermillion.data.repository.MeshListApiImpl
import com.bittelasia.vermillion.domain.usecases.MeshUsesCases
import com.bittelasia.vermillion.domain.usecases.appitem.GetAppItem
import com.bittelasia.vermillion.domain.usecases.broadcast.GetBroadcastResult
import com.bittelasia.vermillion.domain.usecases.channel.GetChannelResult
import com.bittelasia.vermillion.domain.usecases.concierge.GetConciergeResult
import com.bittelasia.vermillion.domain.usecases.config.GetConfigResult
import com.bittelasia.vermillion.domain.usecases.facilties.GetFacilitiesCatResult
import com.bittelasia.vermillion.domain.usecases.facilties.GetFacilitiesResult
import com.bittelasia.vermillion.domain.usecases.guest.GetGuestResult
import com.bittelasia.vermillion.domain.usecases.license.GetLicenseResult
import com.bittelasia.vermillion.domain.usecases.message.GetMessageResult
import com.bittelasia.vermillion.domain.usecases.message.GetReadMessageResult
import com.bittelasia.vermillion.domain.usecases.post_stat.PostStatistics
import com.bittelasia.vermillion.domain.usecases.register.GetRegistrationResult
import com.bittelasia.vermillion.domain.usecases.show_online.GetOnlineDevices
import com.bittelasia.vermillion.domain.usecases.themes.GetThemesBGResult
import com.bittelasia.vermillion.domain.usecases.themes.GetThemesResult
import com.bittelasia.vermillion.domain.usecases.time.GetTimeResult
import com.bittelasia.vermillion.domain.usecases.weather.GetWeatherResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMeshListRepository(
        meshDB: MeshDataBase,
        apiService: MeshApi,
    ): MeshListApiImpl {
        return MeshListApiImpl(
            meshDataBase = meshDB,
            api = apiService
        )
    }

    @Provides
    @Singleton
    fun provideMeshCases(repository: MeshListApiImpl): MeshUsesCases {
        return MeshUsesCases(
            getThemesResult = GetThemesResult(repository),
            getConfigResult = GetConfigResult(repository),
            getAppItem = GetAppItem(repository),
            getWeatherResult = GetWeatherResult(repository),
            getTimeResult = GetTimeResult(repository),
            getMessageResult = GetMessageResult(repository),
            readMessageResult = GetReadMessageResult(repository),
            getFacilitiesResult = GetFacilitiesResult(repository),
            getFacilitiesCatResult = GetFacilitiesCatResult(repository),
            getThemesBGResult = GetThemesBGResult(repository),
            getChannelResult = GetChannelResult(repository),
            getOnlineDevices = GetOnlineDevices(repository),
            postStatistics = PostStatistics(repository),
            guest = GetGuestResult(repository),
            getConciergeResult = GetConciergeResult(repository),
            getBroadcast = GetBroadcastResult(repository),
            getResult = GetRegistrationResult(repository),
            getLicense = GetLicenseResult(repository),
        )
    }
}