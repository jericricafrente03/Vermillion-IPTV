package com.bittelasia.vermillion.data.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bittelasia.vermillion.data.local.BaseUrlInterceptor
import com.bittelasia.vermillion.data.repository.stbpref.manager.STBDataStore.readSTB
import com.bittelasia.vermillion.util.ADB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED){
            CoroutineScope(Dispatchers.IO).launch {
                launch {
                    ADB.exec("svc wifi disable")
                }
                context?.readSTB(Dispatchers.IO){
                    BaseUrlInterceptor.setBaseUrl(it.HOST+":"+it.PORT)
                }
            }
        }
    }
}