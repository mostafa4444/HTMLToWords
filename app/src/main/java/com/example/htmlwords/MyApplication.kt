package com.example.htmlwords

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyApplication : Application() {

    lateinit var executorService: ExecutorService
    lateinit var mainThreadHandler: Handler

    override fun onCreate() {
        super.onCreate()
        executorService = Executors.newFixedThreadPool(3)
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper())
        instance = this
    }

    companion object {
        lateinit var instance: MyApplication
            private set
    }
}