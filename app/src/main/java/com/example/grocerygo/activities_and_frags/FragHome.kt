package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.frag_home.*
import kotlinx.android.synthetic.main.app_toolbar.*
import java.util.zip.Inflater

class FragHome : Fragment() {
    var data = arrayListOf<Category>(Category(catName = "DEFAULT CAT NAME"))
    lateinit var adapter: AdapterCategories
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_home, container, false)
    }

    private fun init() {
        setClickListeners()
        picassoImages()
        requestData()
        setupRecyclerView()
        // fake-bind text_view_hello
        text_view_hello.text = getString(R.string.hello_start, App.sm.user.name)
        //
    }

    private fun setupRecyclerView() {
        recycler_view.layoutManager = GridLayoutManager(activity!!,2)
        adapter = AdapterCategories(activity!!)
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
            // TODO navigate to login screen
        }
    }

    private fun requestData() {
        Log.d("TMLog","requestData`endpoint:"+Endpoints.vCategoryEndpoint)
        var requestQueue = Volley.newRequestQueue(activity!!)
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