package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.setup
import com.example.grocerygo.inheritables.GGToolbarActivity
import com.example.grocerygo.models.LoginObject
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityLogin : GGToolbarActivity() {
    override val title: String
        get() = "Login"
    override val layout: Int
        get() = R.layout.activity_login


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        button_not_yet_registered.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
        }
        button_login_send.setOnClickListener {
            val user = LoginObject(edit_text_email.text.toString(), edit_text_password.text.toString())
            if (App.sm.attemptLogin(user)){
                startActivity(Intent(this, ActivityHost::class.java))
            } else {
                Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
