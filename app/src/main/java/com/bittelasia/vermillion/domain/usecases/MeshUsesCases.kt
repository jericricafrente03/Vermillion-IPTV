package com.bittelasia.vermillion.domain.usecases

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

data class MeshUsesCases(
    val getThemesResult: GetThemesResult,
    val getConfigResult: GetConfigResult,
    val getAppItem: GetAppItem,
    val getWeatherResult: GetWeatherResult,
    val getTimeResult: GetTimeResult,
    val getMessageResult: GetMessageResult,
    val readMessageResult: GetReadMessageResult,
    val getFacilitiesResult: GetFacilitiesResult,
    val getFacilitiesCatResult: GetFacilitiesCatResult,
    val getThemesBGResult: GetThemesBGResult,
    val getChannelResult: GetChannelResult,
    val getOnlineDevices: GetOnlineDevices,
    val postStatistics: PostStatistics,
    val guest: GetGuestResult,
    val getConciergeResult: GetConciergeResult,
    val getBroadcast: GetBroadcastResult,
    val getResult: GetRegistrationResult,
    val getLicense: GetLicenseResult
)