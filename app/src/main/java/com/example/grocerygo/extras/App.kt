package com.example.grocerygo.extras

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.tmcommonkotlin.logz


class App : Application() {
    override fun onCreate() {
        logz("MyApplication`OnCreate")
        super.onCreate()
        instance = this
        notificationManager // Do I need this..?
    }

    companion object {
        lateinit var instance: App
            private set
        val sm by lazy { SessionManager() }
        val sd by lazy { SessionData() }
        val db by lazy { DBConnection() }
        val notificationManager: NotificationManager
            get() {
                val notificationManager =
                    instance.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val name = "GroceryGo main notification channel"
                    val description = "The main GroceryGo Notification channel"
                    val importance = NotificationManager.IMPORTANCE_DEFAULT

                    val notificationChannel =
                        NotificationChannel(Config.NOTIFICATION_CHANNEL, name, importance)
                    notificationChannel.description = description
                    notificationManager.createNotificationChannel(notificationChannel)
                }
                return notificationManager
            }
    }
}