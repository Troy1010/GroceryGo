package com.example.grocerygo.app

import android.app.Application
import android.util.Log
import com.example.grocerygo.extras.SessionManager


class App : Application() {
    override fun onCreate() {
        Log.d("TMLog","MyApplication`OnCreate")
        super.onCreate()
        instance = this
        sessionManager = SessionManager()
    }

    companion object {
        lateinit var instance: App
            private set
        lateinit var sessionManager :SessionManager
            private set
    }
}