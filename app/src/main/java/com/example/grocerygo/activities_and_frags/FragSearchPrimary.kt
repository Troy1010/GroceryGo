package com.example.grocerygo.activities_and_frags

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
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterCategories
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.extras.Title
import com.example.grocerygo.models.Category
import com.example.grocerygo.models.ReceivedCategoriesObject
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_home.*

class FragSearchPrimary : Fragment(), Title {
    override val title = "Search"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_search_primary, container, false)
    }

    private fun setupRecyclerView(categories:ArrayList<Category>) {
        recycler_view.layoutManager = GridLayoutManager(activity!!,2)
        recycler_view.adapter = AdapterCategories(activity!!, categories)
    }

    override fun onStart() { super.onStart();init() }

    private fun init() {
        requestCategories()
    }

    private fun requestCategories() {
        var requestQueue = Volley.newRequestQueue(activity!!)
        var request = StringRequest(
            Request.Method.GET, Endpoints.vCategoryEndpoint,
            Response.Listener { response ->
                var receivedCategoriesObject = GsonBuilder().create()
                    .fromJson(response, ReceivedCategoriesObject::class.java)
                setupRecyclerView(receivedCategoriesObject.data)
            },
            Response.ErrorListener {
                Log.d("TMLog", "Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }
}