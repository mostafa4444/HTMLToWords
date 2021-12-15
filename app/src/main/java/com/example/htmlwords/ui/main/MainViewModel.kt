package com.example.htmlwords.ui.main

import com.example.htmlwords.MyApplication
import com.example.htmlwords.base.BaseViewModel
import com.example.htmlwords.model.WordModel
import com.example.htmlwords.repository.MainRepository
import com.example.htmlwords.utils.DataState
import com.example.htmlwords.repository.local.LocalRepository
import com.example.htmlwords.repository.local.LocalRepositoryImpl
import com.example.htmlwords.repository.local.SQLiteHelper
import com.example.htmlwords.repository.remote.RemoteRepositoryImpl
import com.example.htmlwords.utils.AppConstants
import com.example.htmlwords.utils.HtmlHelper


class MainViewModel : BaseViewModel() {

    var mainRepo: MainRepository = MainRepository(
        MyApplication.instance
    )

    override fun stop() {

    }
    override fun start() {
    }


    fun fetchHTMLfromServer(callback: (DataState<String>) -> Unit){
        MyApplication.instance.executorService.execute {
            callback(DataState.Loading())
            try {
                val response = mainRepo.fetchHtmlFromServer()
                if (response.isNotEmpty() &&  response != AppConstants.EMPTY){
                    mainRepo.deleteModels() // Here i can update the changes words only but with some additional steps
                    mainRepo.insertModel(
                        HtmlHelper.getWordList(
                            HtmlHelper.getStringListFromHtml(response).myWordList!!
                        )
                    )
                    callback(DataState.Success(response))
                }else{
                    callback(DataState.NoData(AppConstants.EMPTY))
                }
            }catch (e: Exception){
                e.printStackTrace()
                callback(DataState.Error())
            }
        }
    }

    fun fetchWordsListFromLocal(callback: (DataState<List<WordModel>>) -> Unit){
        MyApplication.instance.executorService.execute {
            callback(DataState.Loading())
            try {
                val response = mainRepo.fetchModels()
                if (response.isNotEmpty()){
                    callback(DataState.Success(response))
                }else{
                    callback(DataState.NoData(ArrayList()))
                }
            }catch (e: Exception){
                e.printStackTrace()
                callback(DataState.Error())
            }
        }
    }




}