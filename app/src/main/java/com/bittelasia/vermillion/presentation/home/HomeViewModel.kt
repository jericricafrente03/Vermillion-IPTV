package com.bittelasia.vermillion.presentation.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bittelasia.vermillion.data.local.DataState
import com.bittelasia.vermillion.data.manager.Command
import com.bittelasia.vermillion.data.manager.XMPPManager
import com.bittelasia.vermillion.data.repository.stbpref.data.License
import com.bittelasia.vermillion.data.repository.stbpref.data.STB
import com.bittelasia.vermillion.data.repository.stbpref.manager.LicenseDataStore.saveLicense
import com.bittelasia.vermillion.domain.model.app_item.item.AppData
import com.bittelasia.vermillion.domain.model.broadcast.BroadCastData
import com.bittelasia.vermillion.domain.model.channel.item.ChannelData
import com.bittelasia.vermillion.domain.model.concierge.item.ConciergeData
import com.bittelasia.vermillion.domain.model.config.item.SystemData
import com.bittelasia.vermillion.domain.model.facilities.item.FacilitiesData
import com.bittelasia.vermillion.domain.model.message.item.GetMessageData
import com.bittelasia.vermillion.domain.model.post_stat.InputStatistics
import com.bittelasia.vermillion.domain.model.post_stat.PostStatistics
import com.bittelasia.vermillion.domain.model.theme.item.Theme
import com.bittelasia.vermillion.domain.model.theme.item.Zone
import com.bittelasia.vermillion.domain.model.weather.item.GetWeeklyWeatherForecastData
import com.bittelasia.vermillion.domain.usecases.MeshUsesCases
import com.bittelasia.vermillion.util.ADB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val context: Application,
    private val meshUsesCases: MeshUsesCases
) : ViewModel() {

    private val _selectedConfig: MutableStateFlow<DataState<SystemData>?> = MutableStateFlow(null)
    private val _selectedBg: MutableStateFlow<DataState<Theme>?> = MutableStateFlow(null)
    private val _selectedWeather: MutableStateFlow<DataState<GetWeeklyWeatherForecastData>?> = MutableStateFlow(null)
    private var _selectedChannel: MutableStateFlow<DataState<List<ChannelData>>?> = MutableStateFlow(null)
    private val _selectedAppItem: MutableStateFlow<DataState<List<AppData>>?> = MutableStateFlow(null)
    private val _selectedListWeather: MutableStateFlow<DataState<List<GetWeeklyWeatherForecastData>>?> = MutableStateFlow(null)
    private val _selectedMessageData: MutableStateFlow<DataState<List<GetMessageData>>?> = MutableStateFlow(null)
    private val _selectedMessageInfo: MutableStateFlow<DataState<GetMessageData>?> = MutableStateFlow(null)
    private val _selectedFac: MutableStateFlow<DataState<List<FacilitiesData>>?> = MutableStateFlow(null)
    private val _selectedFacImage: MutableStateFlow<DataState<FacilitiesData>?> = MutableStateFlow(null)
    private val _selectedCon: MutableStateFlow<DataState<List<ConciergeData>>?> = MutableStateFlow(null)
    private val _selectedConImage: MutableStateFlow<DataState<ConciergeData>?> = MutableStateFlow(null)
    private val _selectedGuest: MutableStateFlow<String> = MutableStateFlow("")

    val selectedBg: MutableStateFlow<DataState<Theme>?> = _selectedBg
    val selectedConfig: MutableStateFlow<DataState<SystemData>?> = _selectedConfig
    val selectedChannel: MutableStateFlow<DataState<List<ChannelData>>?> = _selectedChannel
    val selectedAppItem: MutableStateFlow<DataState<List<AppData>>?> = _selectedAppItem
    val selectedWeather: MutableStateFlow<DataState<GetWeeklyWeatherForecastData>?> = _selectedWeather
    val selectedListWeather: MutableStateFlow<DataState<List<GetWeeklyWeatherForecastData>>?> = _selectedListWeather
    val selectedMessageData: MutableStateFlow<DataState<List<GetMessageData>>?> = _selectedMessageData
    val selectedMessageInfo: MutableStateFlow<DataState<GetMessageData>?> = _selectedMessageInfo
    val selectedFac: MutableStateFlow<DataState<List<FacilitiesData>>?> = _selectedFac
    val selectedFacImage: MutableStateFlow<DataState<FacilitiesData>?> = _selectedFacImage
    val selectedCon: MutableStateFlow<DataState<List<ConciergeData>>?> = _selectedCon
    val selectedConImage: MutableStateFlow<DataState<ConciergeData>?> = _selectedConImage
    val selectedGuest: MutableStateFlow<String> = _selectedGuest

    private val _themeApplist: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _themeWeatherTop: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _themeWeatherBottom: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _themeGuest: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _themeMessageList: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _themeTimeWeather: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _themeMessageData: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _themeFacilitiesCat: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _themeFacilities: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _themeConciergeCat: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _themeConcierge: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _themeLogo: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _themeWelcome: MutableStateFlow<DataState<Zone>?> = MutableStateFlow(null)
    private val _broadcastReceiver: MutableStateFlow<DataState<BroadCastData>?> = MutableStateFlow(null)

    val messageNo: MutableStateFlow<Int> = MutableStateFlow(0)
    val themeGuest: MutableStateFlow<DataState<Zone>?> = _themeGuest
    val themeTimeWeather: MutableStateFlow<DataState<Zone>?> = _themeTimeWeather
    val themeApplist: MutableStateFlow<DataState<Zone>?> = _themeApplist
    val themeWeatherTop: MutableStateFlow<DataState<Zone>?> = _themeWeatherTop
    val themeWeatherBottom: MutableStateFlow<DataState<Zone>?> = _themeWeatherBottom
    val themeMessageList: MutableStateFlow<DataState<Zone>?> = _themeMessageList
    val themeMessageData: MutableStateFlow<DataState<Zone>?> = _themeMessageData
    val themeFacilitiesCat: MutableStateFlow<DataState<Zone>?> = _themeFacilitiesCat
    val themeFacilities: MutableStateFlow<DataState<Zone>?> = _themeFacilities
    val themeConciergeCat: MutableStateFlow<DataState<Zone>?> = _themeConciergeCat
    val themeConcierge: MutableStateFlow<DataState<Zone>?> = _themeConcierge
    val themeLogo: MutableStateFlow<DataState<Zone>?> = _themeLogo
    val themeWelcome: MutableStateFlow<DataState<Zone>?> = _themeWelcome
    val broadcastReceiver : MutableStateFlow<DataState<BroadCastData>?> = _broadcastReceiver

    init {
        meshAPIFlow()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun allApiFlow() {
        viewModelScope.launch {
            withContext(IO) {
                val urls = listOf(
                    getThemeUrl(),
                    getThemeBg(),
                    getAppItem(),
                    updateTime(),
                )
                val resultFlow: Flow<String> = urls
                    .asFlow()
                    .flatMapConcat { url ->
                        makeApiCall(url)
                    }
                resultFlow.collect { response ->
                    println("Response: $response")
                }
            }
        }
    }

    fun topApiFlow(){
        viewModelScope.launch {
            withContext(IO) {
                val urls = listOf(
                    async(IO) { configResult() },
                    async(IO) { fetchWeather() },
                    async(IO) { getGuest() }
                )
                urls.awaitAll()
            }
        }
    }

    fun facilitiesApiFlow() {
        viewModelScope.launch {
            withContext(IO) {
                val urls = async(IO) { getFacilities() }
                urls.await()
            }
        }
    }
    fun conciergeApiFlow() {
        viewModelScope.launch {
            withContext(IO) {
                val urls = async(IO) { getConcierge() }
                urls.await()
            }
        }
    }
    fun messageApiFlow() {
        viewModelScope.launch {
            withContext(IO) {
                val urls = async(IO) { getMessage() }
                urls.await()
            }
        }
    }
    fun weatherApiFlow() {
        viewModelScope.launch {
            withContext(IO) {
                val urls = async(IO) { fetchWeather() }
                urls.await()
            }
        }
    }


    private fun meshAPIFlow() {
        viewModelScope.launch(IO) {
            try {
                XMPPManager.xmppData.collectLatest {
                    if (it.isNotEmpty()) {
                        when (Command.xmppToAPI(it)) {
                            "Applist" -> {
                                getAppItem()
                            }

                            "Config" -> {
                                configResult()
                            }

                            "Themes" -> {
                                val deferredResponses = listOf(
                                    async(IO) { getThemeBg() },
                                    async(IO) { getThemeUrl() }
                                )
                                deferredResponses.awaitAll()
                            }

                            "Television" -> {
                                getChannel()
                            }

                            "Message" -> {
                                getMessage()
                            }

                            "facility" -> {
                                getFacilities()
                            }

                            "check_stb" -> {
                                showOnline()
                            }

                            "reset" -> {
                                resetSTB()
                            }

                            "settings" -> {
                                showSetting()
                            }

                            "guest_check" -> {
                                getGuest()
                            }

                            "concierge" -> {
                                getConcierge()
                            }
                            "broadcast" -> {
                                getBroadcast()
                            }
                            "reset_license" -> {
                                getResetLicense()
                            }

                            else -> {

                            }
                        }
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun getBroadcast() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getBroadcast().collectLatest {
                    try {
                        _broadcastReceiver.value = it
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
    private fun getResetLicense() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getLicense(context).collectLatest { license ->
                    try {
                        context.apply {
                            license.data?.data?.let { getData ->
                                saveLicense(License.apply {
                                    STATUS = getData.result
                                    END_DATE = getData.endDate
                                    REMAINING_DAYS = getData.remaining_days
                                })
                            }
                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        }
    }
    private fun resetSTB() {
        viewModelScope.launch {
            withContext(IO) {
                ADB.executeADB("pm clear com.bittelasia.vermillion")
            }
        }
    }

    private fun showSetting() {
        viewModelScope.launch {
            withContext(IO) {
                ADB.executeADB("am start -n 'com.android.settings/.Settings'")
            }
        }
    }

    fun getChannel() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getChannelResult().collectLatest {
                    val allChannel = mutableListOf<Int>()
                    try {
                        _selectedChannel.value = it
                        it.data?.forEach { channel ->
                            allChannel.add(channel.id.toInt())
                        }
                        getStatistics(listChannel = allChannel)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun getStatistics(listChannel: MutableList<Int>) {
        viewModelScope.launch {
            withContext(IO) {
                val random = Random(System.currentTimeMillis())
                try {
                    val sdfOutput = SimpleDateFormat("MMM d, yyyy h:mm:ss a", Locale.getDefault())
                    val dateLicence = sdfOutput.format(Date())
                    if (listChannel.size != 0) {
                        val data = Random.nextInt(1, listChannel.size)
                        val randomList = List(5) {
                            PostStatistics(
                                device_id = STB.DEV_ID,
                                start = dateLicence,
                                end = dateLicence,
                                id = random.nextInt(1, 99),
                                item_id = data,
                                item_type = "tv"
                            )
                        }
                        meshUsesCases.postStatistics(InputStatistics(randomList)).collectLatest {}
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getThemeUrl() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getThemesResult().filter { it.data != null }.collectLatest {
                    try {
                        it.data?.forEach { data ->
                            when (data.section) {
                                "GuestAndRoomZone" -> {
                                    _themeGuest.value = DataState.Success(data)
                                }

                                "TimeAndWeatherZone" -> {
                                    _themeTimeWeather.value = DataState.Success(data)
                                }

                                "AppListZone" -> {
                                    _themeApplist.value = DataState.Success(data)
                                }

                                "WeatherTodayZone" -> {
                                    _themeWeatherTop.value = DataState.Success(data)
                                }

                                "WeatherForecastZone" -> {
                                    _themeWeatherBottom.value = DataState.Success(data)
                                }

                                "MessageListZone" -> {
                                    _themeMessageList.value = DataState.Success(data)
                                }

                                "MessageDisplayZone" -> {
                                    _themeMessageData.value = DataState.Success(data)
                                }

                                "HotelInfoCategoryZone" -> {
                                    _themeFacilitiesCat.value = DataState.Success(data)
                                }

                                "HotelInfoItemZone" -> {
                                    _themeFacilities.value = DataState.Success(data)
                                }

                                "LogoZone" -> {
                                    _themeLogo.value = DataState.Success(data)
                                }

                                "WelcomeMessageZone" -> {
                                    _themeWelcome.value = DataState.Success(data)
                                }

                                "ConciergeItemZone" -> {
                                    _themeConcierge.value = DataState.Success(data)
                                }

                                "ConciergeCategoryZone" -> {
                                    _themeConciergeCat.value = DataState.Success(data)
                                }

                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun getAppItem() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getAppItem().filter { it.data != null }.collectLatest {
                    try {
                        _selectedAppItem.value = it
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }



    private fun configResult() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getConfigResult().collectLatest {
                    try {
                        _selectedConfig.value = it
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun getGuest() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.guest().collectLatest { guestData ->
                    try {
                        val data = guestData.data
                        _selectedGuest.value =
                            if (data != null) "${data.firstname} ${data.lastname}"
                            else "Guest"
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun getThemeBg() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getThemesBGResult().collectLatest {
                    try {
                        _selectedBg.value = it
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun getFacilities() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getFacilitiesResult().filter { it.data != null }
                    .collectLatest { ids ->
                        try {
                            _selectedFac.value = ids
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
            }
        }
    }

    fun getFacilitiesImage(id: String) {
        viewModelScope.launch {
            withContext(IO) {
                _selectedFac.collectLatest {
                    try {
                        val filter = it?.data?.find { ids ->
                            ids.id == id
                        }
                        _selectedFacImage.value = DataState.Success(filter)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun getConcierge() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getConciergeResult().filter { it.data != null }.collectLatest {
                    try {
                        _selectedCon.value = it
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun getConciergeImage(id: String) {
        viewModelScope.launch {
            withContext(IO) {
                _selectedCon.collectLatest {
                    try {
                        val filter = it?.data?.find { ids ->
                            ids.id == id
                        }
                        _selectedConImage.value = DataState.Success(filter)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun getMessage() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getMessageResult().filter { it.data != null }.map {
                    val sortDate = it.data
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
                    sortDate?.sortedByDescending { dates ->
                        dates.messg_datetime?.let { sort -> dateFormat.parse(sort) }
                    }
                }.collectLatest {
                    try {
                        val message = it?.filter { data ->
                            data.messg_status == "1" || data.messg_status == "0"
                        }
                        if (message != null) {
                            messageNo.value = message.size
                        }
                        _selectedMessageData.value = DataState.Success(it)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun getSelectedMessage(id: String) {
        viewModelScope.launch {
            withContext(IO) {
                _selectedMessageData.collectLatest {
                    try {
                        val filterMessage = it?.data?.first { message ->
                            message.id == id
                        }
                        if (filterMessage != null) {
                            if (filterMessage.messg_status == "1" || filterMessage.messg_status == "0") {
                                meshUsesCases.readMessageResult(id).collectLatest {}
                                getMessage()
                            }
                            _selectedMessageInfo.value = DataState.Success(filterMessage)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getWeatherResult().filter { it.data != null }
                    .collectLatest { myWeather ->
                        try {
                            myWeather.data?.forEach { list ->
                                val format = list.date?.let { myList ->
                                    SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).parse(myList)
                                }
                                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(format!!)
                                val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                                if (date.equals(todayDate)) {
                                    _selectedWeather.value = DataState.Success(list)
                                }
                            }
                            val result = myWeather.data?.filter { list ->
                                val format = list.date?.let { myList ->
                                    SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).parse(myList)
                                }
                                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(format!!)
                                val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                                date != todayDate
                            }?.take(6)
                            _selectedListWeather.value = DataState.Success(result)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
            }
        }
    }

    private fun updateTime() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getTimeResult().collect {
                    try {
                        it.data?.time?.let { setTime ->
                            val command = "su 0 toybox date $setTime"
                            ADB.exec(command)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun showOnline() {
        viewModelScope.launch {
            withContext(IO) {
                meshUsesCases.getOnlineDevices().collect {}
            }
        }
    }

    private suspend fun makeApiCall(url: Unit): Flow<String> = flow {
        delay(1000)
        emit("Response from $url")
    }

}