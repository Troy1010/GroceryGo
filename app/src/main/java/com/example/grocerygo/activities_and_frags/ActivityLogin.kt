package com.example.grocerygo.activities_and_frags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grocerygo.R
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        button_not_yet_registered.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
        }
    }
}
