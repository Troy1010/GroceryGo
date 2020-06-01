package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.extras.InputValidation
import com.example.grocerygo.models.PostAddressObject
import com.example.grocerygo.models.received.ReceivedPostedAddressObject
import com.example.tmcommonkotlin.logz
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_address.*
import org.json.JSONObject

class ActivityAddress : GGToolbarActivity(layout = R.layout.activity_address),
    View.OnFocusChangeListener, View.OnClickListener {
    override val title = "Update Address"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
    }


    private fun setupListeners() {
        button_address_submit.setOnClickListener(this)
        text_input_street_address.setOnFocusChangeListener(this)
        text_input_apt_num.setOnFocusChangeListener(this)
        text_input_city.setOnFocusChangeListener(this)
        text_input_state.setOnFocusChangeListener(this)
        text_input_zip_code.setOnFocusChangeListener(this)
    }


    private fun postAddress(postAddressObject: PostAddressObject) {
        val requestQueue = Volley.newRequestQueue(this)
        val jsonObject = JSONObject(Gson().toJson(postAddressObject))
        val request = JsonObjectRequest(
            Request.Method.POST, Endpoints.getPostAddressEndpoint(), jsonObject,
            Response.Listener { response ->
                // save as primaryAddress
                val receivedPostedAddressObject = GsonBuilder().create()
                    .fromJson(response.toString(), ReceivedPostedAddressObject::class.java)
                App.sm.primaryAddress = receivedPostedAddressObject.data
                // go back to PaymentInfo
                onBackPressed()
                finish() //TODO just navigate all the way back, without ruining backstack
//                startActivity(Intent(this, ActivityPaymentInfo::class.java))
            },
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }


    override fun onFocusChange(v: View, hasFocus: Boolean) {
        when (v) {
            text_input_street_address -> handleResult(
                InputValidation.asStreetAddress(text_input_street_address.text.toString()),
                text_input_layout_street_address, hasFocus
            )
            text_input_apt_num -> handleResult(
                InputValidation.asAptNum(text_input_apt_num.text.toString()),
                text_input_layout_apt_num, hasFocus
            )
            text_input_city -> handleResult(
                InputValidation.asCity(text_input_city.text.toString()),
                text_input_layout_city, hasFocus
            )
            text_input_state -> handleResult(
                InputValidation.asState(text_input_state.text.toString()),
                text_input_layout_state, hasFocus
            )
            text_input_zip_code -> handleResult(
                InputValidation.asZipCode(text_input_zip_code.text.toString()),
                text_input_layout_zip_code, hasFocus
            )
        }
    }

    fun handleResult(
        validationResult: InputValidation.Result,
        layout: TextInputLayout,
        bClearError: Boolean = false
    ): Boolean {
        if (bClearError) {
            layout.isErrorEnabled = false
            return false
        }
        return when (validationResult) {
            is InputValidation.Result.Error -> {
                layout.setErrorTextAppearance(R.style.ErrorText)
                layout.error = validationResult.msg
                true
            }
            is InputValidation.Result.Warning -> {
                layout.setErrorTextAppearance(R.style.WarningText)
                layout.error = validationResult.msg
                false
            }
            is InputValidation.Result.Success -> {
                layout.editText?.setText(validationResult.correctedValue)
                layout.isErrorEnabled = false
                false
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            button_address_submit -> {
                logz("button_address_submit")
                var areAnyErrors = handleResult(
                    InputValidation.asStreetAddress(text_input_street_address.text.toString()),
                    text_input_layout_street_address)
                areAnyErrors = handleResult(
                    InputValidation.asAptNum(text_input_apt_num.text.toString()),
                    text_input_layout_apt_num) || areAnyErrors
                areAnyErrors = handleResult(
                    InputValidation.asCity(text_input_city.text.toString()),
                    text_input_layout_city) || areAnyErrors
                areAnyErrors = handleResult(
                    InputValidation.asState(text_input_state.text.toString()),
                    text_input_layout_state) || areAnyErrors
                areAnyErrors = handleResult(
                    InputValidation.asZipCode(text_input_zip_code.text.toString()),
                    text_input_layout_zip_code) || areAnyErrors
                if (!areAnyErrors) {
                    val addressNameAndNum =
                        splitAddressIntoNumAndName(text_input_street_address.text.toString())
                    postAddress(
                        PostAddressObject(
                            city = text_input_city.text.toString(),
                            location = text_input_state.text.toString(),
                            mobile = App.sm.user?.mobile!!,
                            name = App.sm.user?.name!!,
                            pincode = text_input_zip_code.text.toString(),
                            streetName = addressNameAndNum?.name ?: "",
                            type = "Mobile",
                            userId = App.sm.user?._id.toString(),
                            houseNo = addressNameAndNum?.num.toString()
                        )
                    )
                }
            }
        }
    }
}

data class StreetNumAndName(val num: Int, val name: String)


fun splitAddressIntoNumAndName(address: String): StreetNumAndName? {
    if (address.length == 0) {
        logz("splitAddressIntoNumAndName`String too short")
        return null
    }
    if (!address[0].isDefined()) {
        logz("splitAddressIntoNumAndName`No Number")
        return null
    }
    var endPos = 0
    for (i in 0 until address.length) {
        if (!address[i].isDigit()) {
            endPos = i
            break
        }
    }
    return if (endPos == 0) {
        null
    } else {
        StreetNumAndName(
            address.substring(0, endPos).toInt(),
            address.substring(endPos, address.length).trim()
        )
    }
}