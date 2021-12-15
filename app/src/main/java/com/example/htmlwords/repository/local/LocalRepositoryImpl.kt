package com.example.htmlwords.repository.local

import android.database.sqlite.SQLiteDatabase
import com.example.htmlwords.model.WordModel
import com.example.htmlwords.network.NetworkUtility
import com.example.htmlwords.utils.AppConstants

class LocalRepositoryImpl(
    private val database: SQLiteHelper
) : LocalRepository {
    override fun insertModel(models: List<WordModel>) {
        database.insertListOfModels(models)
    }

    override fun fetchModels(): List<WordModel> {
        return database.getAllModels()
    }

    override fun deleteModels() {
        database.deleteData()
    }


}