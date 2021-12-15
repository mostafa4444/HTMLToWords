package com.example.htmlwords.ui.main

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.htmlwords.R
import com.example.htmlwords.base.BaseActivity
import com.example.htmlwords.databinding.ActivityMainBinding
import com.example.htmlwords.model.WordModel
import com.example.htmlwords.ui.main.adapter.WordsAdapter
import com.example.htmlwords.utils.DataState
import com.example.htmlwords.utils.Utils
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.htmlwords.utils.WidgetHelper.gone
import com.example.htmlwords.utils.WidgetHelper.visible
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    lateinit var adapter: WordsAdapter
    private var myDataList = mutableListOf<WordModel>()
    private var isSortedAscending = false
    override fun initView() {
        adapter = WordsAdapter()
        baseViewBinding.sortBtn.setOnClickListener(this)
        baseViewBinding.searchBtn.setOnClickListener(this)
        baseViewBinding.reloadBtn.setOnClickListener(this)
    }


    override fun getContentView(): Int = R.layout.activity_main

    override fun initializeViewModel() {
        baseViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        fetchDataFromServer()
    }

    override fun onClick(v: View?) {
        when(v){
            baseViewBinding.searchBtn->{
                filter(baseViewBinding.searchEdt.text.toString())
            }
            baseViewBinding.sortBtn->{
                sortAction()
            }
            baseViewBinding.reloadBtn->{
                fetchDataFromServer()
            }
        }
    }

    private fun sortAction(){
        if (myDataList.isNotEmpty()){
            val myList = adapter.getDataList().toTypedArray()
            if (isSortedAscending){
                myList.sortByDescending { it.count }
            }else{
                myList.sortBy { it.count }
            }
            isSortedAscending = !isSortedAscending
            adapter.submitMyList(myList.toList())
        }
    }

    private fun fetchDataFromServer(){
        baseViewModel?.fetchHTMLfromServer {
            runOnUiThread {
                when (it) {
                    is DataState.Success -> {
                        Log.d("Success", "MAinAc ${it.data}")
                        hideVisibilityPerRequest()
                        fetchDataFromLocalStorge()
                    }
                    is DataState.Error -> {
                        if (Utils.isNetworkConnected(this)) {
                            showDialog("Something went wrong", "OK", null)
                        } else {
                            showDialog("Network Not Connected", "OK", null)
                        }
                        hideVisibilityPerRequest()
                        fetchDataFromLocalStorge()
                    }
                    is DataState.NoData -> {
                        fetchDataFromLocalStorge()
                        hideVisibilityPerRequest()
                    }
                    else -> {
                        viewVisibilityPerRequest()
                    }
                }
            }
        }
    }

    private fun fetchDataFromLocalStorge(){
        baseViewModel?.fetchWordsListFromLocal {
            runOnUiThread {
                when (it) {
                    is DataState.Success -> {
                        hideVisibilityPerRequest()
                        myDataList = it.data!!.toMutableList()
                        initializeAdapter()
                    }
                    is DataState.Error -> {
                        if (Utils.isNetworkConnected(this)){
                            showDialog("Something went wrong" , "OK" , null)
                        }else{
                            showDialog("Network Not Connected" , "OK" , null)
                        }
                        hideVisibilityPerRequest()
                    }
                    is DataState.NoData -> {
                        showDialog("No Data Found" , "OK" , null)
                        hideVisibilityPerRequest()
                    }
                    else -> {
                        viewVisibilityPerRequest()
                    }
                }
            }
        }
    }

    private fun initializeAdapter(){
        val dividerItemDecoration = DividerItemDecoration(
            baseViewBinding.rvWords.context,
            DividerItemDecoration.VERTICAL
        )
        baseViewBinding.rvWords.addItemDecoration(dividerItemDecoration)
        baseViewBinding.rvWords.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.apply {
            submitMyList(myDataList)
        }
        baseViewBinding.rvWords.adapter = adapter
        baseViewBinding.rvWords.startLayoutAnimation()
    }

    private fun viewVisibilityPerRequest(){
        baseViewBinding.progressCircular.visible()
        baseViewBinding.operationLinear.gone()
    }




    private fun hideVisibilityPerRequest(){
        baseViewBinding.operationLinear.visible()
        baseViewBinding.progressCircular.gone()
    }

    private fun filter(text: String) {
        val txt = text.lowercase(Locale.ROOT)
        val filteredNames = ArrayList<WordModel>()

        myDataList.filterTo(filteredNames) {
            it.word.lowercase(Locale.ROOT).contains(txt.lowercase(Locale.ROOT))
        }
        adapter.filterList(filteredNames)
    }

}