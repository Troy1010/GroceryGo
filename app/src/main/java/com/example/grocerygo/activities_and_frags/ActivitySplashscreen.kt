package com.example.grocerygo.activities_and_frags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.grocerygo.R
import com.example.tmcommonkotlin.logz

class ActivitySplashscreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        this.setTheme(R.style.AppTheme)
        setContentView(R.layout.app_spashscreen)
        logz("I'M ALIVE")
        init()
    }

    private fun init() {
        var handler = Handler()
        handler.postDelayed( {
            startLandingActivity()
        }, 1000)
    }

    private fun startLandingActivity() {
        ActivityHost.start(this, ActivityHost.TabEnum.Home)
        finish()
    }

}
