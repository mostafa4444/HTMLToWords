package com.example.htmlwords.repository.remote

interface RemoteRepository {
    fun fetchHtmlFromServer() : String
}