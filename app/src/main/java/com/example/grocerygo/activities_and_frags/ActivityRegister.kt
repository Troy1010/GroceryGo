package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import com.example.grocerygo.extras.AppCompatActivityWithToolbarFunctionality
import com.example.grocerygo.extras.hasDigit
import com.example.grocerygo.extras.isAllDigits
import com.example.grocerygo.extras.setup
import com.example.grocerygo.models.User
import com.google.android.material.textfield.TextInputLayout
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
            var name = text_input_name.text.toString().trim()
            var email = text_input_email.text.toString().trim()
            var password = text_input_password.text.toString().trim()
            var mobile = text_input_mobile.text.toString().trim()
            var errorHandler = ErrorHandler()
            errorHandler.handle(FormValidator.name(name), text_input_layout_name)
            errorHandler.handle(FormValidator.password(password), text_input_layout_password)
            errorHandler.handle(FormValidator.email(email), text_input_layout_email)
            errorHandler.handle(FormValidator.mobile(mobile), text_input_layout_mobile)
            if (!errorHandler.foundError) {
                App.sm.register(User(name, email, password)) // TODO take mobile
                startActivity(Intent(this, ActivityHome::class.java))
            }
        }
        toolbar_top.setup(this, "Register")
    }

}

class ErrorHandler {
    var foundError = false
    fun handle(error:ValidationError?, v:TextInputLayout) {
        if (error != null) {
            v.error = error.msg
            foundError = true
        } else {
            v.isErrorEnabled = false
        }
    }
}

data class ValidationError (
    var msg:String
)

object FormValidator {
    fun name(name:String):ValidationError? {
        if (name.isNullOrEmpty()){
            return ValidationError("Required")
        }
        return null
    }
    fun email(email:String):ValidationError? {
        if (email.isNullOrEmpty()){
            return ValidationError("Required")
        } else if (!email.contains("@")) {
            return ValidationError("Must contain an @")
        }
        return null
    }
    fun password(password:String):ValidationError? {
        if (password.isNullOrEmpty()){
            return ValidationError("Required")
        } else if (password.length < 6) {
            return ValidationError("Mst have at least 6 characters")
        } else if (!password.hasDigit()) {
            return ValidationError("Must contain at least 1 digit")
        }
        return null
    }
    fun mobile(mobile:String):ValidationError? {
        if (mobile.isNullOrEmpty()){
            return ValidationError("Required")
        } else if (!(mobile.isAllDigits())) {
            return ValidationError("Must only contain digits")
        } else if (mobile.length != 10) {
            return ValidationError("Must have 10 characters")
        }
        return null
    }

}