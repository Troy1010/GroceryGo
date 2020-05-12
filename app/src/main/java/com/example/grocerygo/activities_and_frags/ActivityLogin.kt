package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import com.example.grocerygo.extras.AppCompatActivityWithToolbarFunctionality
import com.example.grocerygo.extras.setup
import com.example.grocerygo.models.LoginObject
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityLogin : AppCompatActivityWithToolbarFunctionality() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        setupClickListeners()
        toolbar_top.setup(this, "Login")
    }

    private fun setupClickListeners() {
        button_not_yet_registered.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
        }
        button_login_send.setOnClickListener {
            val user = LoginObject(edit_text_email.text.toString(), edit_text_password.text.toString())
            if (App.sm.attemptLogin(user)){
                startActivity(Intent(this, ActivityHome::class.java))
            } else {
                Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
