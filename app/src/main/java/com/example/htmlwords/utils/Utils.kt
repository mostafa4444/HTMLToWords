package com.example.htmlwords.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log


@Suppress("DEPRECATION")
object Utils {

    fun isNetworkConnected(context: Context): Boolean {
        var connected = false
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message.toString())
        }
        return connected
    }
}