@file:Suppress("DEPRECATION")

package com.bittelasia.vermillion.util

import android.os.Build
import eu.chainfire.libsuperuser.Shell
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader

object ADB {
    private  var result: String? = null

    suspend fun result(adb: String): String {
        val adbs: Deferred<String> = CoroutineScope(Dispatchers.IO).async {
            try {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
                    val process = withContext(Dispatchers.IO) {
                        Runtime.getRuntime().exec(adb)
                    }
                    val reader = BufferedReader(InputStreamReader(process.inputStream))
                    val macAddress: String = withContext(Dispatchers.IO) {
                            reader.readLine()
                        }.trim()
                    withContext(Dispatchers.IO) {
                        reader.close()
                    }
                    withContext(Dispatchers.IO) {
                        process.waitFor()
                    }
                    return@async macAddress
                }else{
                    val suAvailable = Shell.SU.available()
                    if (suAvailable) {
                    Shell.SU.version(false)
                    Shell.SU.version(true)
                    val suResult = Shell.SU.run(
                        arrayOf(
                            adb
                        )
                    )
                    return@async suResult.toString().replace("[", "").replace("]", "")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return@async ""
        }
        return adbs.await()
    }
    suspend fun exec(adb: String) {
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
                val process = withContext(Dispatchers.IO) {
                    Runtime.getRuntime().exec(adb)
                }
                val reader = BufferedReader(InputStreamReader(process.inputStream))
                withContext(Dispatchers.IO) {
                    reader.close()
                }
                withContext(Dispatchers.IO) {
                    process.waitFor()
                }
            }else {
                val suAvailable = Shell.SU.available()
                if (suAvailable) {
                    Shell.SU.version(false)
                    Shell.SU.version(true)
                    val suResult = Shell.SU.run(arrayOf(adb))
                    result = suResult.toString()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun executeADB(adb: String) {
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
                val suAvailable = Shell.SU.available()
                if (suAvailable) {
                    Shell.SU.version(false)
                    Shell.SU.version(true)
                    val suResult = Shell.SU.run(arrayOf(adb))
                    result = suResult.toString()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}