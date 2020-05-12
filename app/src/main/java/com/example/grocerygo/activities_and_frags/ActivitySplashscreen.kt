package com.example.grocerygo.activities_and_frags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import com.example.grocerygo.extras.Config
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_spashscreen.*

class ActivitySplashscreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_spashscreen)
        Log.d("TMLog", "I'M ALIVE")
        init()
    }

    private fun init() {
        var handler = Handler()
        handler.postDelayed( {
            startLandingActivity()
        }, 1000)
    }

    private fun startLandingActivity() {
        var intent = if(App.sm.isLoggedIn()) {
            Intent(this, ActivityWithHome::class.java)
        } else {
            Intent(this, ActivityWithLogin::class.java)
        }
        startActivity(intent)
        finish()
    }

}
