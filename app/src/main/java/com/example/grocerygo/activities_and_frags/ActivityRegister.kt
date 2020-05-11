package com.example.grocerygo.activities_and_frags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import com.example.grocerygo.models.User
import kotlinx.android.synthetic.main.activity_register.*

class ActivityRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        button_register_send.setOnClickListener {
            var name = edit_text_name.text.toString().trim()
            var email = edit_text_email.text.toString().trim()
            var password = edit_text_password.text.toString().trim()
            App.sm.register(User(name, email, password))
            startActivity(Intent(this, ActivityHome::class.java))
        }
    }
}
