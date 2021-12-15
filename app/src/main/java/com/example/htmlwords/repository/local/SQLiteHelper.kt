package com.example.htmlwords.repository.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.htmlwords.model.WordModel
import com.example.htmlwords.utils.SQLiteConstants

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        SQLiteConstants.DATABASE_NAME,
        null,
        SQLiteConstants.DATABASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQLiteConstants.createTbQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQLiteConstants.dropTbQuery)
        onCreate(db)
    }

    fun insertModel(model: WordModel): Long{
        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(SQLiteConstants.NAME , model.word)
        contentValue.put(SQLiteConstants.COUNT , model.count)
        val success = db.insert(SQLiteConstants.WORD_TABLE , null , contentValue)
        db.close()
        return success
    }

    fun getAllModels(): ArrayList<WordModel>{
        val myList = ArrayList<WordModel>()
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(SQLiteConstants.SELECT_FROM_MAIN , null)
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(SQLiteConstants.SELECT_FROM_MAIN)
            return ArrayList()
        }

        if (cursor.moveToFirst()){
            do {
                val item = WordModel(
                    word = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteConstants.NAME)),
                    count = cursor.getInt(cursor.getColumnIndexOrThrow(SQLiteConstants.COUNT))
                )
                myList.add(item)
            }while (cursor.moveToNext())
        }
        return myList
    }

    fun insertListOfModels(myList: List<WordModel>){
        myList.forEach { model ->
            insertModel(model)
        }
    }

    fun deleteData(){
        this.writableDatabase.delete(SQLiteConstants.WORD_TABLE , null , null)
    }

}