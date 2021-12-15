package com.example.htmlwords.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.htmlwords.databinding.CounterItemBinding
import com.example.htmlwords.model.WordModel

class WordsAdapter (myList: List<WordModel> = listOf()) :
    RecyclerView.Adapter<WordsAdapter.ViewHolder>() {


    var trxList: List<WordModel> = ArrayList()
    private var parent: ViewGroup? = null

    init {
        this.trxList = myList
    }

    fun getDataList():List<WordModel>{
        return this.trxList
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: CounterItemBinding =
            CounterItemBinding.inflate(layoutInflater, parent, false)
        this.parent = parent
        return ViewHolder(itemBinding)
    }

    fun submitMyList(myList: List<WordModel>) {
        this.trxList = myList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return trxList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myItemTX = trxList[position]
        holder.bind(holder.myItemTX!!)
    }



    inner class ViewHolder(var itemBinding: CounterItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root){
        var myItemTX: WordModel? = null

        fun bind(myItem: WordModel?) {
            itemBinding.myItem = myItem
        }
    }

    fun filterList(filteredList: ArrayList<WordModel>) {
        this.trxList = filteredList
        notifyDataSetChanged()
    }

}