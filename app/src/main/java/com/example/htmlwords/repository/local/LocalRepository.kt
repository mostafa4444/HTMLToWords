package com.example.htmlwords.repository.local

import com.example.htmlwords.model.WordModel

interface LocalRepository {
    fun insertModel(models:List<WordModel>)
    fun fetchModels(): List<WordModel>
    fun deleteModels()
}