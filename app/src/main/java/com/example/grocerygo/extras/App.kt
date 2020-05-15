package com.example.grocerygo.extras

import android.app.Application
import android.util.Log


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
        val db by lazy { DBHelper() }
    }
}