package com.example.grocerygo.extras

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.models.*
import com.example.tmcommonkotlin.logz
import com.google.gson.Gson
import org.json.JSONObject

object Requester {
    val requestQueue = Volley.newRequestQueue(App.instance)

    fun requestCategories(listener: Response.Listener<JSONObject>) {
        var request = JsonObjectRequest(
            Request.Method.GET, Endpoints.categories, null,
            listener,
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }


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
    fun requestOrders(userID:String?, listener: Response.Listener<JSONObject>) {
        if (userID == null) {
            logz("requestOrders received a null userID")
            return
        }
        val request = JsonObjectRequest(
            Request.Method.GET, Endpoints.getOrdersEndpoint(userID), null,
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


    fun requestLogin(loginObject: LoginObject, listener: Response.Listener<JSONObject>) {
        val jsonObject = JSONObject(Gson().toJson(loginObject))
        val request = JsonObjectRequest(
            Request.Method.POST, Endpoints.login, jsonObject,
            listener,
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }

    fun requestOrderPlacement(listener: Response.Listener<JSONObject>? = null, errorListener:Response.ErrorListener? = null) {
        val listenerToUse = listener ?: Response.Listener<JSONObject> { }
        val errorListenerToUse = errorListener?: Response.ErrorListener {
            logz("Response.ErrorListener`it:$it")
        }
        val user = App.sm.user!!
        val address = App.sm.primaryAddress!!
        val products = App.db.getProducts()
        val orderSummary = OrderSummary(products)
        val objectToPost = Order(
            user = user,
            userId = user._id!!,
            shippingAddress = address,
            products = products,
            orderSummary = OrderSummary_PASSABLE(
                deliveryCharges = orderSummary.deliveryFee,
                orderAmount = orderSummary.grandTotal
            ),
            orderStatus = "Getting Ready" // TODO
        )
        val jsonObject = JSONObject(Gson().toJson(objectToPost))
        val request = JsonObjectRequest(
            Request.Method.POST, Endpoints.getPostOrderEndpoint(), jsonObject,
            listenerToUse,
            errorListenerToUse)
        requestQueue.add(request)
    }
}