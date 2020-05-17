package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterCategories
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.inheritables.GGActivityCallbacks
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.models.Category
import com.example.grocerygo.models.ReceivedCategoriesObject
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_home.*
import kotlinx.android.synthetic.main.frag_search_primary.*

class FragSearchPrimary : TMFragment() {
    val title = "Search"
    override val layout: Int
        get() = R.layout.frag_search_primary

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val returning = super.onCreateView(inflater, container, savedInstanceState)
        requestCategories()
        return returning
    }

    override fun onStart() {
        super.onStart()
        var activityZ = activity as AppCompatActivity
        if (activityZ is GGActivityCallbacks) {
            activityZ.setToolbarTitle(title)
        }
    }

    private fun setupRecyclerView(categories:ArrayList<Category>) {
        recycler_view_categories.layoutManager = GridLayoutManager(activity!!,2)
        recycler_view_categories.adapter = AdapterCategories(activity!!, categories)
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