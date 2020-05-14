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
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.extras.Title
import com.example.grocerygo.models.ReceivedCategoriesObject
import com.example.grocerygo.models.Category
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.frag_home.*

class FragHome : Fragment(), Title {
    override val title = "Home"
    private val recyclerAdapter: AdapterCategories by lazy { AdapterCategories(activity!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        setClickListeners()
        picassoImages()
        requestCategories()
        setupRecyclerView()
        // fake-bind text_view_hello
        text_view_hello.text = getString(R.string.hello_start, App.sm.user.name)
        //
    }

    private fun setupRecyclerView() {
        recycler_view.layoutManager = GridLayoutManager(activity!!,2)
        recycler_view.adapter = recyclerAdapter
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
            startActivity(Intent(activity!!,ActivityLogin::class.java))
        }
    }

    private fun requestCategories() {
        var requestQueue = Volley.newRequestQueue(activity!!)
        var request = StringRequest(Request.Method.GET, Endpoints.vCategoryEndpoint,
            Response.Listener {response ->
                recyclerAdapter.data = GsonBuilder().create().fromJson(response,ReceivedCategoriesObject::class.java).data
            },
            Response.ErrorListener {
                Log.d("TMLog", "Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }
}
