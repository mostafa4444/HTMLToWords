package com.example.htmlwords.utils

import android.os.Build
import android.text.Html
import com.example.htmlwords.model.WebResponse
import com.example.htmlwords.model.WordModel
import java.util.*

object HtmlHelper {
    fun getStringListFromHtml(input: String): WebResponse {
        val bodyText = input.substringAfterLast("<body>").substringBeforeLast("</body>")
        val lines = getTextLines(bodyText = bodyText)
        val pageContent =
            lines.filter { it.groupValues[1].isNotEmpty() }
                .map { it.groupValues[1] }.toList()
                .joinToString(" ")


        return WebResponse(pageContent.split(" "))
    }

    fun getWordList(stringList: List<String>): List<WordModel> {
        val words = mutableListOf<WordModel>()
        for (item in stringList.distinct()) words.add(
            WordModel(
                word = convertHtmlToString(item),
                count = Collections.frequency(stringList, item)
            )
        )
        return words
    }

    private fun convertHtmlToString(input: String): String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY).toString()
        else
            Html.fromHtml(input).toString()


    private val contentRegex = Regex("content=\"(.*?)[\">]")
    private fun getTextLines(bodyText: String) = contentRegex.findAll(bodyText)
}