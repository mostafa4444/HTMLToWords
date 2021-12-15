package com.example.htmlwords.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    abstract fun stop()
    abstract fun start()
    var networkStatus = false

}