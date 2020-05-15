package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterProducts
import com.example.grocerygo.extras.*
import com.example.grocerygo.models.Product
import com.example.grocerygo.models.ReceivedProductsObject
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_search_lower.recycler_view_products

class FragSearchLower : Fragment(), Title {
    override val title = "Search"
    private var catID: Int = 1
    private var subCatID: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init()
        return inflater.inflate(R.layout.frag_search_lower, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subCatID = it.getInt(KEY_SUB_CAT_ID)
            catID = it.getInt(KEY_CAT_ID)
        }
    }

    private fun init() {
        requestProducts(subCatID)
    }

    private fun setupRecyclerView(products: ArrayList<Product>) {
        recycler_view_products.layoutManager = LinearLayoutManager(activity!!)
        recycler_view_products.adapter = AdapterProducts(activity!!, products)
        recycler_view_products
            .addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
    }

    private fun requestProducts(subCatID: Int) {
        val endpoint = Endpoints.getSelectedProductsEndpoint(subCatID)
        logz("requestProducts`Endpoint:$endpoint")
        var requestQueue = Volley.newRequestQueue(context)
        var request = JsonObjectRequest(
            Request.Method.GET, endpoint, null,
            Response.Listener { response ->
                var gson = GsonBuilder().create()
                var receivedProductsObject =
                    gson.fromJson(response.toString(), ReceivedProductsObject::class.java)
                // give to AdapterProducts
                setupRecyclerView(receivedProductsObject.data)
            },
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(catID: Int = 1, subCatID: Int = 0) =
            FragSearchLower().apply {
                arguments = Bundle().apply {
                    putInt(KEY_CAT_ID, catID)
                    putInt(KEY_SUB_CAT_ID, subCatID)
                }
            }
    }


}
