package com.example.grocerygo.activities_and_frags

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
import com.example.grocerygo.models.ReceivedSubCategoriesObject
import com.example.grocerygo.models.SubCategory
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_home.*
import kotlinx.android.synthetic.main.frag_search.*
import kotlinx.android.synthetic.main.frag_search.recycler_view_products

class FragSearch : Fragment(), Title {
    override val title = "Search"
    var catID: Int = 1
    var subCatID: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logz("FragSearch`onCreateView")
        return inflater.inflate(R.layout.frag_search, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logz("FragSearch`onCreate")
        arguments?.let {
            subCatID = it.getInt(KEY_SUB_CAT_ID)
            logz("FragSearch`subCatID:$subCatID")
            catID = it.getInt(KEY_CAT_ID)
            logz("FragSearch`catID:$catID")

        }
    }

    override fun onStart() {
        super.onStart();init()
    }

    private fun init() {
        logz("FragSearch`Init")
        requestSubCategoryData(catID)
        requestProducts(subCatID)
    }

    private fun setupRecyclerView(products: ArrayList<Product>) {
        recycler_view_products.layoutManager = LinearLayoutManager(activity!!)
        recycler_view_products.adapter = AdapterProducts(activity!!, products)
        recycler_view_products
            .addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
    }

    private fun setupTabLayout(subCategories: ArrayList<SubCategory>) {
        for (subCategory in subCategories) {
            var selected = subCategory.subId == subCatID
            tab_layout.addTab(tab_layout.newTab().setText(subCategory.subName), selected)
        }
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                navigateToTab((tab?.position ?: 0) + 1)
            }
        })
    }

    private fun navigateToTab(subCatID: Int) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_fragments, newInstance(catID, subCatID))
            ?.setTransition(FragmentTransaction.TRANSIT_NONE)
            ?.commit()
    }

    private fun requestSubCategoryData(catID: Int) {
        val endpoint = Endpoints.getSelectedSubCategoriesEndpoint(catID)
        var requestQueue = Volley.newRequestQueue(context)
        var request = JsonObjectRequest(
            Request.Method.GET, endpoint, null,
            Response.Listener { response ->
                var gson = GsonBuilder().create()
                var subCategoriesObjectZ =
                    gson.fromJson(response.toString(), ReceivedSubCategoriesObject::class.java)
                // give data to tab_layout
                setupTabLayout(subCategoriesObjectZ.data)
            },
            Response.ErrorListener {
                logz("requestSubCategoryData`Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }

    private fun requestProducts(subCatID: Int) {
        val endpoint = Endpoints.getSelectedProductsEndpoint(subCatID)
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
                Log.d("TMLog", "Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(catID: Int = 1, subCatID: Int = 1) =
            FragSearch().apply {
                arguments = Bundle().apply {
                    putInt(KEY_CAT_ID, catID)
                    putInt(KEY_SUB_CAT_ID, subCatID)
                    logz("Sending subID:$subCatID")
                }
            }
    }


}
