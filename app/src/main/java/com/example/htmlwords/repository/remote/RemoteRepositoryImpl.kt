package com.example.htmlwords.repository.remote

import com.example.htmlwords.network.NetworkUtility
import com.example.htmlwords.utils.AppConstants

class RemoteRepositoryImpl : RemoteRepository {
    override fun fetchHtmlFromServer(): String {
        return NetworkUtility.fetchHtmlWenRequest(AppConstants.INSTABUG_URL)
    }
}