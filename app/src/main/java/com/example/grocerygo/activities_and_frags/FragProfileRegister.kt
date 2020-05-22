package com.example.grocerygo.activities_and_frags

import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.extras.*
import com.example.grocerygo.inheritables.HostCallbacks
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.inheritables.ToolbarCallbacks
import com.example.grocerygo.models.received.ReceivedRegistrationObject
import com.example.grocerygo.models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_register.*
import org.json.JSONObject


class FragProfileRegister : TMFragment() {
    override val layout: Int
        get() = R.layout.frag_register

    override fun onStart() {
        super.onStart()
        setupParent()
        setupListeners()
    }

    private fun setupParent() {
        (activity as HostCallbacks).showNavigationBar(true)
        (activity as ToolbarCallbacks).showCart(true)
        (activity as ToolbarCallbacks).showBack(true)
        (activity as ToolbarCallbacks).setTitle("Register")
    }

    private fun setupListeners() {
        text_input_email.setOnFocusChangeListener(MyOnFocusChangeListener(text_input_email,text_input_layout_email,RegFieldEnum.EMAIL))
        text_input_name.setOnFocusChangeListener(MyOnFocusChangeListener(text_input_name,text_input_layout_name,RegFieldEnum.NAME))
        text_input_password.setOnFocusChangeListener(MyOnFocusChangeListener(text_input_password,text_input_layout_password,RegFieldEnum.PASSWORD))
        text_input_mobile.setOnFocusChangeListener(MyOnFocusChangeListener(text_input_mobile,text_input_layout_mobile,RegFieldEnum.MOBILE))
        button_register_send.setOnClickListener {
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
                        requestRegistration(User(name, email, password, mobile))
//                        App.sm.user = User(name, email, password, mobile)
                    }
        }
    }



    private fun requestRegistration(user: User) {
        val requestQueue = Volley.newRequestQueue(activity!!)
        val params = HashMap<String, String>()
        params["email"] = user.email!!
        params["password"] = user.password!!
        params["firstName"] = user.name!!
        params["mobile"] = user.mobile!!
        //typecast params into jsonObject
        val jsonObject = JSONObject(params as Map<*, *>)

        val request = JsonObjectRequest(
            Request.Method.POST, Endpoints.register, jsonObject,
            Response.Listener { response ->
                val receivedCategoriesObject = GsonBuilder().create()
                    .fromJson(response.toString(), ReceivedRegistrationObject::class.java)
                //
                val registrationData = receivedCategoriesObject.data
                App.sm.user = User(
                    name = registrationData.firstName,
                    email = registrationData.email,
                    password = registrationData.password,
                    mobile = registrationData.mobile,
                    _id = registrationData._id
                )
                logz("App.sm.user:${App.sm.user}")
                //
                (activity as HostCallbacks).goToHome()
            },
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
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