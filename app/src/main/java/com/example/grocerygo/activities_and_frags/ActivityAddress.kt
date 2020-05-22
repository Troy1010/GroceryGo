package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.extras.logz
import com.example.grocerygo.inheritables.GGToolbarActivity
import com.example.grocerygo.models.PostAddressObject
import com.example.grocerygo.models.received.ReceivedPostedAddressObject
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_c_address.*
import org.json.JSONObject

class ActivityAddress : GGToolbarActivity() {
    override val title: String
        get() = "Update Address"
    override val layout: Int
        get() = R.layout.activity_c_address

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_address_submit.setOnClickListener {
            val addressNameAndNum = splitAddressIntoNumAndName(text_input_street_address.text.toString())
            postAddress(
                PostAddressObject(
                    city = text_input_city.text.toString(),
                    location = text_input_state.text.toString(),
                    mobile = App.sm.user.mobile!!,
                    name = App.sm.user.name!!,
                    pincode = text_input_zip_code.text.toString(),
                    streetName = addressNameAndNum?.name ?: "",
                    type = "Mobile",
                    userId = App.sm.user.id.toString(),
                    houseNo = addressNameAndNum?.num.toString()
                )
            )
        }
    }

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
        var v = StreetNumAndName(
            address.substring(0, endPos).toInt(),
            address.substring(endPos, address.length).trim()
        )
        return v
    }


    private fun postAddress(postAddressObject: PostAddressObject) {
        val requestQueue = Volley.newRequestQueue(this)
        val params = HashMap<String, String>()
        params["city"] = postAddressObject.city
        params["location"] = postAddressObject.location
        params["mobile"] = postAddressObject.mobile
        params["name"] = postAddressObject.name
        params["pincode"] = postAddressObject.pincode
        params["streetName"] = postAddressObject.streetName
        params["type"] = postAddressObject.type
        params["userId"] = postAddressObject.userId
        params["houseNo"] = postAddressObject.houseNo
        //typecast params into jsonObject
        val jsonObject = JSONObject(params as Map<*, *>)

        val request = JsonObjectRequest(
            Request.Method.POST, Endpoints.getPostAddressEndpoint(), jsonObject,
            Response.Listener { response ->
                val receivedPostedAddressObject = GsonBuilder().create()
                    .fromJson(response.toString(), ReceivedPostedAddressObject::class.java)
                // do nothing with the response and just go back
                startActivity(Intent(this, ActivityPaymentInfo::class.java))
            },
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }
}

data class StreetNumAndName(val num: Int, val name: String)