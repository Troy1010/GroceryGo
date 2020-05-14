package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
                startActivity(Intent(this, FragHome::class.java))
            }
        }
        text_input_email.addTextChangedListener(MyTextWater(text_input_layout_email, RegInputType.EMAIL))
        text_input_password.addTextChangedListener(MyTextWater(text_input_layout_password, RegInputType.PASSWORD))
        text_input_mobile.addTextChangedListener(MyTextWater(text_input_layout_mobile, RegInputType.MOBILE))
        text_input_name.addTextChangedListener(MyTextWater(text_input_layout_name, RegInputType.NAME))
        toolbar_top.setup(this, "Register")
    }

}
enum class RegInputType {
    EMAIL, NAME, PASSWORD, MOBILE
}

class MyTextWater (var v: TextInputLayout, var e:RegInputType): TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        ErrorHandler().handle(FormValidator.validateType(e,s.toString()),v)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        v.isErrorEnabled = false
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

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
    fun validateType(e:RegInputType, s:String):ValidationError? {
        return when (e) {
            RegInputType.EMAIL -> this.email(s)
            RegInputType.NAME -> this.name(s)
            RegInputType.PASSWORD -> this.password(s)
            RegInputType.MOBILE -> this.mobile(s)
        }
    }
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