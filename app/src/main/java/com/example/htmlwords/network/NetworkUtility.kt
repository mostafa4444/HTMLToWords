package com.example.htmlwords.network

import com.example.htmlwords.utils.AppConstants
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

object NetworkUtility {

    fun fetchHtmlWenRequest(url:String): String{
        var html:String
        val connectionURL = URL(url)
        (connectionURL.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "GET"
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            setRequestProperty("Accept", "*/*")
//                doOutput = true
            val reader = BufferedReader(InputStreamReader(inputStream))
            val str = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                str.append(line)
            }
            inputStream.close()
            html = str.toString()
//                Log.e("Call Result " , getStringListFromHtml(html).toString())
            return html
        }
        return AppConstants.EMPTY
    }

}