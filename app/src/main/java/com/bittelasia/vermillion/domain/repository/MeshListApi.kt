package com.bittelasia.vermillion.domain.repository

import android.content.Context
import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.domain.model.app_item.item.AppData
import com.bittelasia.vermillion.domain.model.broadcast.BroadCastData
import com.bittelasia.vermillion.domain.model.channel.item.ChannelData
import com.bittelasia.vermillion.domain.model.concierge.item.ConciergeData
import com.bittelasia.vermillion.domain.model.config.item.SystemData
import com.bittelasia.vermillion.domain.model.facilities.category.FacilitiesCategoryData
import com.bittelasia.vermillion.domain.model.facilities.item.FacilitiesData
import com.bittelasia.vermillion.domain.model.guest.item.GuestData
import com.bittelasia.vermillion.domain.model.license.response.LicenseDate
import com.bittelasia.vermillion.domain.model.message.item.GetMessageData
import com.bittelasia.vermillion.domain.model.message.readmessage.ReadMessage
import com.bittelasia.vermillion.domain.model.post_stat.InputStatistics
import com.bittelasia.vermillion.domain.model.post_stat.StatResponse
import com.bittelasia.vermillion.domain.model.result.Register
import com.bittelasia.vermillion.domain.model.stb.StbRegistration
import com.bittelasia.vermillion.domain.model.theme.item.Theme
import com.bittelasia.vermillion.domain.model.theme.item.Zone
import com.bittelasia.vermillion.domain.model.time.Time
import com.bittelasia.vermillion.domain.model.weather.item.GetWeeklyWeatherForecastData
import kotlinx.coroutines.flow.Flow

interface MeshListApi {
    fun themesResult(): Flow<DataState<List<Zone>>>
    fun themesBgResult(): Flow<DataState<Theme>>
    fun configResult(): Flow<DataState<SystemData>>
    fun uiIconResult(): Flow<DataState<List<AppData>>>
    fun weatherResult(): Flow<DataState<List<GetWeeklyWeatherForecastData>>>
    fun messageResult(): Flow<DataState<List<GetMessageData>>>
    fun facilitiesResult(): Flow<DataState<List<FacilitiesData>>>
    fun facilitiesCategoryResult(): Flow<DataState<List<FacilitiesCategoryData>>>
    fun channelResult(): Flow<DataState<List<ChannelData>>>
    fun readMessageResult(id: String): Flow<DataState<ReadMessage>>
    fun time(): Flow<DataState<Time>>
    fun showOnline(): Flow<DataState<String>>
    fun postStatistics(inputStatistics: InputStatistics): Flow<DataState<StatResponse>>
    fun guestResult(): Flow<DataState<GuestData>>
    fun conciergeResult(): Flow<DataState<List<ConciergeData>>>
    fun broadcast(): Flow<DataState<BroadCastData>>
    fun registerResult(stbRegistration: StbRegistration): Flow<DataState<Register>>
    suspend fun postResult(app: Context): Flow<DataState<LicenseDate>>
}