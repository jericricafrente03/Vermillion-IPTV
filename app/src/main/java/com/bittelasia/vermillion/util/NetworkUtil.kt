package com.bittelasia.vermillion.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import okhttp3.internal.and
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Locale
import java.util.Random


object NetworkUtil {
    suspend fun hashedDeviceMac(context : Context?): String {
        return convertToHash(
            macAddress =  macAddress(context),
            appVersion = "4.0"
        ).replace("%0A","")
    }
    private fun convertToHash(macAddress: String, appVersion: String = "4.0"): String {
        var sb: StringBuffer? = null
        try {
            val mac = macAddress+appVersion
            val md = MessageDigest.getInstance("MD5")
            md.update(mac.toByteArray())
            val byteData = md.digest()
            sb = StringBuffer()
            for (i in byteData.indices) {
                sb.append(
                    ((byteData[i] and 0xff) + 0x100)
                        .toString(16)
                        .substring(1)
                )
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return sb.toString()
    }
    suspend fun macAddress(context: Context?): String {
        return try {
            ADB.result("cat /sys/class/net/eth0/address")
                .lowercase(Locale.getDefault())
                .filter { !it.isWhitespace() }
                .replace(":", "")
        } catch (e: Exception) {
            if (context != null) {
                getWifiMac(context)
            } else
                randomMACAddress()
                    .lowercase(Locale.getDefault())
                    .filter { !it.isWhitespace() }
                    .replace(":", "")
        }
    }
    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getWifiMac(context: Context): String {
        val manager = context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val info = manager.connectionInfo
        return info.macAddress.lowercase(Locale.getDefault()).filter { !it.isWhitespace() }
    }

    private fun randomMACAddress(): String {
        val rand = Random()
        val macAddr = ByteArray(6)
        rand.nextBytes(macAddr)
        macAddr[0] = (macAddr[0] and 254).toByte()
        val sb = StringBuilder(18)
        for (b in macAddr) {
            if (sb.isNotEmpty()) sb.append(":")
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }
}