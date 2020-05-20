package com.example.grocerygo.activities_and_frags

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterCategories
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.inheritables.ActivityHostCallbacks
import com.example.grocerygo.inheritables.GGToolbarActivityCallbacks
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.models.Category
import com.example.grocerygo.models.ReceivedCategoriesObject
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_search_primary.*

class FragSearchPrimary : TMFragment() {
    val title = "Search"
    override val layout: Int
        get() = R.layout.frag_search_primary

    override fun onCreateViewInit() {
        requestCategories()
    }

    override fun onStart() {
        super.onStart()
        (activity as GGToolbarActivityCallbacks).setToolbarAttributes(title, true)
        (activity as ActivityHostCallbacks).setNavigationEmpty(false)
    }

    private fun setupRecyclerView(categories:ArrayList<Category>) {
        recycler_view_categories?.layoutManager = GridLayoutManager(activity!!,2)
        recycler_view_categories?.adapter = AdapterCategories(activity!!, categories)
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