package com.example.htmlwords.utils

import android.view.View

object WidgetHelper {
    fun View.visible(){
        this.visibility = View.VISIBLE
    }

    fun View.gone(){
        this.visibility = View.GONE
    }

}