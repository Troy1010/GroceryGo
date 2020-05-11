package com.example.grocerygo.activities_and_frags

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterProducts
import com.example.grocerygo.extras.Endpoints
import kotlinx.android.synthetic.main.frag_products.*
import com.android.volley.Request
import com.android.volley.Response
import com.example.grocerygo.extras.SUB_ID_KEY
import com.example.grocerygo.models.ProductsData
import com.google.gson.GsonBuilder

class FragProducts : Fragment() {
    var subID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subID = it.getInt(SUB_ID_KEY)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_products, container, false)
    }

    override fun onStart() {
        super.onStart()
        init()
    }


    fun init() {
        Log.d("TMLog", "FragProducts Init. subID:$subID")
        // setup recycler_view_products
        recycler_view_products.layoutManager = LinearLayoutManager(activity as Context)
        recycler_view_products.adapter =
            AdapterProducts(
                activity as Context
            )
        requestProducts(subID)
    }

    private fun requestProducts(selectedSubID: Int) {
        val endpoint = Endpoints.getSelectedProductsEndpoint(selectedSubID)
        Log.d("TMLog", "requestProducts`Requesting from endpoint:$endpoint")
        var requestQueue = Volley.newRequestQueue(activity as Context)
        var request = JsonObjectRequest(
            Request.Method.GET, endpoint, null,
            Response.Listener { response ->
                var gson = GsonBuilder().create()
                var productsData = gson.fromJson(response.toString(), ProductsData::class.java)
                // give data to adapter
                var mAdapter = recycler_view_products.adapter
                if (mAdapter is AdapterProducts) {
                    mAdapter.data = productsData.data
                }
            },
            Response.ErrorListener {
                Log.d("TMLog", "Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            FragProducts().apply {
                Log.d("TMLog", "newInstance` param1:$param1")
                arguments = Bundle().apply {
                    putInt(SUB_ID_KEY, param1)
                }
            }
    }
}