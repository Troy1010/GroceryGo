package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.adapters.AdapterCategories
import com.example.grocerygo.R
import com.example.grocerygo.app.App
import com.example.grocerygo.extras.AppCompatActivityWithToolbarFunctionality
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.extras.setup
import com.example.grocerygo.models.CategoryData
import com.example.grocerygo.models.Category
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityHome : AppCompatActivityWithToolbarFunctionality() {
    var data = arrayListOf<Category>(Category(catName = "DEFAULT CAT NAME"))
    lateinit var adapter: AdapterCategories

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private fun init() {
        setClickListeners()
        picassoImages()
        requestData()
        setupRecyclerView()
        // fake-bind text_view_hello
        text_view_hello.text = getString(R.string.hello_start, App.sm.user.name)
        //
        toolbar_top.setup(this, "GroceryGo")
    }

    private fun setupRecyclerView() {
        recycler_view.layoutManager = GridLayoutManager(this,2)
        adapter = AdapterCategories(this)
        recycler_view.adapter = adapter
    }
    private fun picassoImages() {
        Picasso
            .get()
            .load(Endpoints.HOME_IMAGE)
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
    }

    private fun requestData() {
        Log.d("TMLog","requestData`endpoint:"+Endpoints.vCategoryEndpoint)
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(Request.Method.GET, Endpoints.vCategoryEndpoint,
            Response.Listener {response ->
                var allData = GsonBuilder().create().fromJson(response,CategoryData::class.java)
                adapter.data = allData.data // TODO rename data to a more understandable name
            },
            Response.ErrorListener {
                Log.d("TMLog", "Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }
}
