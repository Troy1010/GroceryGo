package com.example.grocerygo.extras

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.inheritables.HostCallbacks
import com.example.grocerygo.models.ReceivedAddressesObject
import com.example.grocerygo.models.ReceivedLoginObject
import com.example.grocerygo.models.User
import com.google.gson.GsonBuilder
import org.json.JSONObject

object Requester {
    val requestQueue = Volley.newRequestQueue(App.instance)


    fun requestAddresses(userID:String?, listener: Response.Listener<JSONObject>) {
        if (userID == null) {
            logz("requestAddress received a null userID")
            return
        }
        val request = JsonObjectRequest(
            Request.Method.GET, Endpoints.getAddressesEndpoint(userID), null,
            listener,
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }

    fun requestDeleteAddress(addressID:String, listener: Response.Listener<JSONObject>) {
        val request = JsonObjectRequest(
            Request.Method.DELETE, Endpoints.getDeleteAddressEndpoint(addressID), null,
            listener,
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }


    fun requestLogin(user: User, listener: Response.Listener<JSONObject>) {
        val params = HashMap<String, String>()
        params["email"] = user.email!!
        params["password"] = user.password!!
        //typecast params into jsonObject
        val jsonObject = JSONObject(params as Map<*, *>)

        val request = JsonObjectRequest(
            Request.Method.POST, Endpoints.login, jsonObject,
            listener,
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }
}