package com.example.grocerygo.extras

import android.app.Application


class App : Application() {
    override fun onCreate() {
        logz("MyApplication`OnCreate")
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
        val sm by lazy { SessionManager() }
        val sd by lazy { SessionData() }
        val db by lazy { DatabaseConnection() }
    }
}