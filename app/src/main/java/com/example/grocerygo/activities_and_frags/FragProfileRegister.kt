package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.HostCallbacks
import com.example.grocerygo.activities_and_frags.Inheritables.ToolbarCallbacks
import com.example.grocerygo.extras.*
import com.example.grocerygo.activities_and_frags.Inheritables.TMFragment
import com.example.grocerygo.models.received.ReceivedRegistrationObject
import com.example.grocerygo.models.User
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_profile_register.*
import org.json.JSONObject


class FragProfileRegister : TMFragment(layout = R.layout.frag_profile_register),
    View.OnFocusChangeListener {

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


    override fun onFocusChange(v: View, hasFocus: Boolean) {
        when (v) {
            text_input_name -> handleResult(
                InputValidation.asName(text_input_name.text.toString()),
                text_input_layout_name, hasFocus
            )
            text_input_email -> handleResult(
                InputValidation.asEmail(text_input_email.text.toString()),
                text_input_layout_email, hasFocus
            )
            text_input_password -> handleResult(
                InputValidation.asPassword(text_input_password.text.toString()),
                text_input_layout_password, hasFocus
            )
            text_input_mobile -> handleResult(
                InputValidation.asPhone(text_input_mobile.text.toString()),
                text_input_layout_mobile, hasFocus
            )
        }
    }

    fun handleResult(validationResult:InputValidation.Result, layout: TextInputLayout, bClearError:Boolean=false):Boolean {
        if (bClearError) {
            layout.isErrorEnabled = false
            return false
        }
        when (validationResult) {
            is InputValidation.Result.Error -> {
                layout.error = validationResult.msg
                return true
            }
            is InputValidation.Result.Success -> {
                layout.isErrorEnabled = false
                return false
            }
        } // TODO make this .exhaustive
        return false
    }

    private fun setupListeners() {
        text_input_name.setOnFocusChangeListener(this)
        text_input_email.setOnFocusChangeListener(this)
        text_input_password.setOnFocusChangeListener(this)
        text_input_mobile.setOnFocusChangeListener(this)
        button_register_send.setOnClickListener {
            val name = text_input_name.text.toString().trim()
            val email = text_input_email.text.toString().trim()
            val password = text_input_password.text.toString().trim()
            val phone = text_input_mobile.text.toString().trim()
            var areAnyErrors = handleResult(InputValidation.asName(name), text_input_layout_name)
            areAnyErrors = areAnyErrors || handleResult(InputValidation.asEmail(email), text_input_layout_email)
            areAnyErrors = areAnyErrors || handleResult(InputValidation.asPassword(password), text_input_layout_password)
            areAnyErrors = areAnyErrors || handleResult(InputValidation.asPhone(phone), text_input_layout_mobile)
            if (!areAnyErrors) {
                requestRegistration(User(name, email, password, phone))
            }
        }
        button_go_to_login.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frame_fragments, FragProfileLogin()).commit()
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
                if (App.sm.goToPaymentInfoAfterLogin) {
                    App.sm.goToPaymentInfoAfterLogin = false
                    startActivity(Intent(activity!!, ActivityPaymentInfo::class.java))
                } else {
                    (activity as HostCallbacks).goToTab(ActivityHost.TabEnum.Home)
                }
            },
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }
}