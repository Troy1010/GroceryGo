package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.grocerygo.R
import com.example.grocerygo.extras.Validator
import com.example.grocerygo.extras.logz

class ActivitySplashscreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        this.setTheme(R.style.AppTheme)
        setContentView(R.layout.app_spashscreen)
        logz("I'M ALIVE")
        init2()
        init()
    }

    private fun init2() {
        Validator.Name.lambda()
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
