package com.example.grocerygo.activities_and_frags

import android.view.View
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.hasDigit
import com.example.grocerygo.extras.isAllDigits
import com.example.grocerygo.inheritables.HostCallbacks
import com.example.grocerygo.inheritables.GGFragment
import com.example.grocerygo.inheritables.GGToolbarActivity
import com.example.grocerygo.models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.frag_register.*


class FragProfileRegister : GGFragment() {
    override val title: String
        get() = "Register"
    override val layout: Int
        get() = R.layout.frag_register

    override fun onStart() {
        super.onStart()
        (activity as HostCallbacks).setNavigationEmpty(false)
        (activity as GGToolbarActivity).toolbarMenu?.findItem(R.id.menu_cart)?.isVisible = true
        text_input_email.setOnFocusChangeListener(MyOnFocusChangeListener(text_input_email,text_input_layout_email,RegFieldEnum.EMAIL))
        text_input_name.setOnFocusChangeListener(MyOnFocusChangeListener(text_input_name,text_input_layout_name,RegFieldEnum.NAME))
        text_input_password.setOnFocusChangeListener(MyOnFocusChangeListener(text_input_password,text_input_layout_password,RegFieldEnum.PASSWORD))
        text_input_mobile.setOnFocusChangeListener(MyOnFocusChangeListener(text_input_mobile,text_input_layout_mobile,RegFieldEnum.MOBILE))
        button_register_send.setOnClickListener {v ->
            when (v?.id) {
                R.id.button_register_send -> {
                    var name = text_input_name.text.toString().trim()
                    var email = text_input_email.text.toString().trim()
                    var password = text_input_password.text.toString().trim()
                    var mobile = text_input_mobile.text.toString().trim()
                    var errorHandler = ErrorHandler()
                    errorHandler.handle(FormValidator.name(name), text_input_layout_name)
                    errorHandler.handle(
                        FormValidator.password(password),
                        text_input_layout_password
                    )
                    errorHandler.handle(FormValidator.email(email), text_input_layout_email)
                    errorHandler.handle(FormValidator.mobile(mobile), text_input_layout_mobile)
                    if (!errorHandler.foundError) {
                        App.sm.user = User(name, email, password, mobile)
                        (activity as HostCallbacks).goToHome()
                    }
                }
            }
        }
    }
}

class MyOnFocusChangeListener(var textInputEditText: TextInputEditText, var layoutOfTextInput: TextInputLayout, var e:RegFieldEnum) : View.OnFocusChangeListener {
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            ErrorHandler().handle(
                FormValidator.validate(e,
                    textInputEditText.text.toString()
                ), layoutOfTextInput)
        } else {
            layoutOfTextInput.isErrorEnabled = false
        }
    }

}

enum class RegFieldEnum { EMAIL, NAME, PASSWORD, MOBILE }


class ErrorHandler {
    var foundError = false
    fun handle(error: ValidationError?, layoutOfTextInput: TextInputLayout) {
        if (error != null) {
            layoutOfTextInput.error = error.msg
            foundError = true
        } else {
            layoutOfTextInput.isErrorEnabled = false
        }
    }
}

data class ValidationError(var msg: String)

object FormValidator {
    fun validate(e: RegFieldEnum, stringToValidate: String): ValidationError? {
        return when (e) {
            RegFieldEnum.EMAIL -> this.email(stringToValidate)
            RegFieldEnum.NAME -> this.name(stringToValidate)
            RegFieldEnum.PASSWORD -> this.password(stringToValidate)
            RegFieldEnum.MOBILE -> this.mobile(stringToValidate)
        }
    }

    fun name(name: String): ValidationError? {
        if (name.isEmpty()) {
            return ValidationError("Required")
        }
        return null
    }

    fun email(email: String): ValidationError? {
        if (email.isEmpty()) {
            return ValidationError("Required")
        } else if (!email.contains("@")) {
            return ValidationError("Must contain an @")
        }
        return null
    }

    fun password(password: String): ValidationError? {
        if (password.isEmpty()) {
            return ValidationError("Required")
        } else if (password.length < 6) {
            return ValidationError("Mst have at least 6 characters")
        } else if (!password.hasDigit()) {
            return ValidationError("Must contain at least 1 digit")
        }
        return null
    }

    fun mobile(mobile: String): ValidationError? {
        if (mobile.isEmpty()) {
            return ValidationError("Required")
        } else if (!(mobile.isAllDigits())) {
            return ValidationError("Must only contain digits")
        } else if (mobile.length != 10) {
            return ValidationError("Must have 10 characters")
        }
        return null
    }

}