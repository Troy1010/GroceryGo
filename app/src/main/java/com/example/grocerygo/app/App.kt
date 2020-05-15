package com.example.grocerygo.app

import android.app.Application
import android.util.Log
import com.example.grocerygo.extras.SessionData
import com.example.grocerygo.extras.SessionManager


class App : Application() {
    override fun onCreate() {
        Log.d("TMLog","MyApplication`OnCreate")
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
        val sm by lazy { SessionManager() }
        val sd by lazy { SessionData() }
    }
}