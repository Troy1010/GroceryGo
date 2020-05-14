package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import com.example.grocerygo.extras.AppCompatActivityWithToolbarFunctionality
import com.example.grocerygo.extras.setup
import com.example.grocerygo.models.User
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityRegister : AppCompatActivityWithToolbarFunctionality() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        button_register_send.setOnClickListener {
            var valid = true
            var name = text_input_name.text.toString().trim()
            var email = text_input_email.text.toString().trim()
            var password = text_input_password.text.toString().trim()
            var mobile = text_input_mobile.text.toString().trim()
            if (!FormValidator.name(name)) {
                text_input_layout_name.error = "Name is required"
                valid = false
            }
            if (!FormValidator.email(email)) {
                text_input_layout_email.error = "Email is required"
                valid = false
            }
            if (!FormValidator.password(password)) {
                text_input_layout_password.error = "Password is required"
                valid = false
            }
            if (!FormValidator.mobile(mobile)) {
                text_input_layout_password.error = "Phone number is required"
                valid = false
            }
            if (valid) {
                App.sm.register(User(name, email, password)) // TODO take mobile
                startActivity(Intent(this, ActivityHome::class.java))
            }
        }
        toolbar_top.setup(this, "Register")
    }

}

object FormValidator {
    fun name(name:String):Boolean {
        return !name.isNullOrEmpty()
    }
    fun email(email:String):Boolean {
        return (!email.isNullOrEmpty()) and email.contains("@")
    }
    fun password(password:String):Boolean {
        return !password.isNullOrEmpty() and (password.length >= 6)
    }
    fun mobile(mobile:String):Boolean {
        return !mobile.isNullOrEmpty() and (mobile.length == 10)
    }
}