package com.example.htmlwords.repository.local

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.htmlwords.model.WordModel
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SQLiteHelperTest {
    lateinit var database: SQLiteHelper
    private val model1 = WordModel("This Name" , 20)
    private val model2 = WordModel("This Name2" , 40)
    private val model3 = WordModel("This Name3" , 60)


    @Before
    fun setUp() {
        database = SQLiteHelper(ApplicationProvider.getApplicationContext())
        database.writableDatabase
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testPreConditions() {
        assertNotNull(database)
    }

    @Test
    fun testAddRaw() {
        database.insertModel(model1)
        val models: List<WordModel> = database.getAllModels()
        ViewMatchers.assertThat(models.size, CoreMatchers.`is`(1))
        assertTrue(models[0].word == model1.word)
        assertTrue(models[0].count == model1.count)
        deleteAll()
    }

    @Test
    fun testData() {
        database.insertModel(model1)
        database.insertModel(model2)
        database.insertModel(model3)
        val models: List<WordModel> = database.getAllModels()
        assertEquals(model1.word, models[0].word)
        assertEquals(model2.word, models[1].word)
        assertEquals(model3.word, models[2].word)
        assertEquals(model1.count, models[0].count)
        assertEquals(model2.count, models[1].count)
        assertEquals(model3.count, models[2].count)
        deleteAll()
    }

    @Test
    fun insertListOfModels_return_True(){
        val models = arrayListOf<WordModel>(model1 , model2 , model3)
        database.insertListOfModels(models)
        val fetchedModels = database.getAllModels()
        assertThat(fetchedModels.size, `is`(models.size))
        deleteAll()
    }

    private fun deleteAll() {
        database.deleteData()
    }
}