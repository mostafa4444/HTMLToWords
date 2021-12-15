package com.example.htmlwords.utils

object SQLiteConstants {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "words_database"
    const val WORD_TABLE = "word_table"
    const val ID = "id"
    const val NAME = "name"
    const val COUNT = "count"



    // --------------- Queries -------------------
    const val createTbQuery = ("CREATE TABLE $WORD_TABLE ($NAME TEXT PRIMARY KEY, $COUNT INTEGER)")
    const val dropTbQuery = "DROP TABLE IF EXISTS $WORD_TABLE"
    const val SELECT_FROM_MAIN = "SELECT * FROM $WORD_TABLE"
}