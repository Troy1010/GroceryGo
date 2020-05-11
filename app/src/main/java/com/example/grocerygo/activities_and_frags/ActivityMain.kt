package com.example.grocerygo.activities_and_frags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.adapters.AdapterCategories
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.models.CategoryData
import com.example.grocerygo.models.Category
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {
    var data = arrayListOf<Category>(Category(catName = "DEFAULT CAT NAME"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TMLog", "I'M ALIVE")
        if(!App.sessionManager.isLoggedIn()){
            startActivity(Intent(this, ActivityLogin::class.java))
            finish()
        }
        init()
    }

    private fun init() {
        // Picasso image_view
        Picasso
            .get()
            .load("https://www.incimages.com/uploaded_files/image/970x450/GettyImages-840253474_349532.jpg")
            .placeholder(R.drawable.not_found)
            .error(R.drawable.no_image_available_vector_illustration_260nw_744886198)
            .into(image_view)
        //
        requestData()
        // setup recycler_view
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recycler_view.adapter =AdapterCategories(this)
    }

    private fun requestData() {
        Log.d("TMLog","requestData`endpoint:"+Endpoints.vCategoryEndpoint)
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(Request.Method.GET, Endpoints.vCategoryEndpoint, null,
            Response.Listener {response ->
                var gson = GsonBuilder().create()
                var allData = gson.fromJson(response.toString(),CategoryData::class.java)
                data = allData.data
                var mAdapter = recycler_view.adapter
                if (mAdapter is AdapterCategories) {
                    mAdapter.data = data
                }
            },
            Response.ErrorListener {
                Log.d("TMLog", "Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }
}
