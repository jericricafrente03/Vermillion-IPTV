package com.bittelasia.vermillion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bittelasia.vermillion.domain.model.app_item.dao.AppDataDao
import com.bittelasia.vermillion.domain.model.app_item.item.AppData
import com.bittelasia.vermillion.domain.model.channel.dao.ChannelDao
import com.bittelasia.vermillion.domain.model.channel.item.ChannelData
import com.bittelasia.vermillion.domain.model.concierge.dao.ConciergeDataDao
import com.bittelasia.vermillion.domain.model.concierge.item.ConciergeData
import com.bittelasia.vermillion.domain.model.config.dao.ConfigDao
import com.bittelasia.vermillion.domain.model.config.item.SystemData
import com.bittelasia.vermillion.domain.model.facilities.category.FacilitiesCategoryData
import com.bittelasia.vermillion.domain.model.facilities.dao.FacilitiesCategoryDao
import com.bittelasia.vermillion.domain.model.facilities.dao.FacilitiesDao
import com.bittelasia.vermillion.domain.model.facilities.item.FacilitiesData
import com.bittelasia.vermillion.domain.model.guest.dao.GuestDao
import com.bittelasia.vermillion.domain.model.guest.item.GuestData
import com.bittelasia.vermillion.domain.model.message.dao.GetMessageDao
import com.bittelasia.vermillion.domain.model.message.item.GetMessageData
import com.bittelasia.vermillion.domain.model.theme.dao.ThemeDao
import com.bittelasia.vermillion.domain.model.theme.dao.ZoneDao
import com.bittelasia.vermillion.domain.model.theme.item.Theme
import com.bittelasia.vermillion.domain.model.theme.item.Zone
import com.bittelasia.vermillion.domain.model.weather.dao.GetWeeklyWeatherForecastDao
import com.bittelasia.vermillion.domain.model.weather.item.GetWeeklyWeatherForecastData

@Database(
    entities = [
        Zone::class,
        SystemData::class,
        AppData::class,
        GetWeeklyWeatherForecastData::class,
        GetMessageData::class,
        FacilitiesData::class,
        FacilitiesCategoryData::class,
        Theme::class,
        ChannelData::class,
        GuestData::class,
        ConciergeData::class
    ], version = 1 , exportSchema = false
)
abstract class MeshDataBase : RoomDatabase() {
    abstract fun themeDao() : ThemeDao
    abstract fun zoneDao(): ZoneDao
    abstract fun configDao() : ConfigDao
    abstract fun appDataDao(): AppDataDao
    abstract fun weatherDao(): GetWeeklyWeatherForecastDao
    abstract fun getMessageDao(): GetMessageDao
    abstract fun facilitiesDao(): FacilitiesDao
    abstract fun facilitiesCategoryDao(): FacilitiesCategoryDao
    abstract fun channelDao(): ChannelDao
    abstract fun getGuestDao(): GuestDao
    abstract fun getConciergeDao(): ConciergeDataDao

}