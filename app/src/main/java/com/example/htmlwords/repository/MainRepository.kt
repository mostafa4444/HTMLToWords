package com.example.htmlwords.repository

import android.content.Context
import com.example.htmlwords.MyApplication
import com.example.htmlwords.model.WordModel
import com.example.htmlwords.repository.local.LocalRepository
import com.example.htmlwords.repository.local.LocalRepositoryImpl
import com.example.htmlwords.repository.local.SQLiteHelper
import com.example.htmlwords.repository.remote.RemoteRepository
import com.example.htmlwords.repository.remote.RemoteRepositoryImpl

class MainRepository(
    context: Context
) : LocalRepository , RemoteRepository{

    var localRepository: LocalRepository = LocalRepositoryImpl(SQLiteHelper(context = context))
    var remoteRepository: RemoteRepository = RemoteRepositoryImpl()

    override fun insertModel(models: List<WordModel>) {
        localRepository.insertModel(models)
    }

    override fun fetchModels(): List<WordModel> {
       return localRepository.fetchModels()
    }

    override fun deleteModels() {
        localRepository.deleteModels()
    }

    override fun fetchHtmlFromServer(): String {
        return remoteRepository.fetchHtmlFromServer()
    }
}