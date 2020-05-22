package com.example.grocerygo.extras

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.models.ReceivedAddressesObject
import com.google.gson.GsonBuilder
import org.json.JSONObject

object Requester {

    fun requestAddresses(listener: Response.Listener<JSONObject>) {
        var userID = App.sm.user.id
        if (userID == null) {
            logz("requestAddress received a null userID")
            return
        } else {
            userID = userID!!
        }
        val requestQueue = Volley.newRequestQueue(App.instance) // TODO make a global
        val params = HashMap<String, String>()
        // TODO probably don't need params..
        val jsonObject = JSONObject(params as Map<*, *>)

        val request = JsonObjectRequest(
            Request.Method.GET, Endpoints.getAddressesEndpoint(userID), jsonObject,
            listener,
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }

    fun requestDeleteAddress(addressID:String, listener: Response.Listener<JSONObject>) {
        var addressID = addressID
        val requestQueue = Volley.newRequestQueue(App.instance) // TODO make a global
        val params = HashMap<String, String>()
        // TODO probably don't need params..
        val jsonObject = JSONObject(params as Map<*, *>)

        val request = JsonObjectRequest(
            Request.Method.DELETE, Endpoints.getDeleteAddressEndpoint(addressID), jsonObject,
            listener,
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }
}