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
import com.example.grocerygo.extras.Config
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.models.CategoryData
import com.example.grocerygo.models.Category
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class ActivityHome : AppCompatActivity() {
    var data = arrayListOf<Category>(Category(catName = "DEFAULT CAT NAME"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        setClickListeners()
        picassoImages()
        requestData()
        setupRecyclerView()
        // fake-bind text_view_hello
        text_view_hello.text = getString(R.string.hello_start, App.sm.user.name)
    }
    private fun setupRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recycler_view.adapter =AdapterCategories(this)
    }
    private fun picassoImages() {
        Picasso
            .get()
            .load(Config.DIRECT_ENDPOINT_HOME_IMAGE)
            .placeholder(R.drawable.not_found)
            .error(R.drawable.no_image_available_vector_illustration_260nw_744886198)
            .into(image_view)
    }
    private fun setClickListeners() {
        button_logout.setOnClickListener {
            App.sm.logout()
            startActivity(Intent(this, ActivityLogin::class.java))
            finish()
        }
        button_logout_without_clearing_registration.setOnClickListener {
            startActivity(Intent(this, ActivityLogin::class.java))
        }
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
