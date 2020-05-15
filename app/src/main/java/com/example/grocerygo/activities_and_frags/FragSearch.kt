package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.extras.*
import com.example.grocerygo.models.ReceivedSubCategoriesObject
import com.example.grocerygo.models.SubCategory
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_search.*

class FragSearch : Fragment(), Title {
    override val title = "Search"
    private var bundle:Bundle? = null
    private val catID by lazy { bundle?.getInt(KEY_CAT_ID, 0) ?: 0 }
    private val subCatID by lazy { bundle?.getInt(KEY_SUB_CAT_ID, 0) ?: 0}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logz("FragSearch`onCreateView")
        bundle = savedInstanceState
        return inflater.inflate(R.layout.frag_search,container,false)
    }

    override fun onStart() { super.onStart();init()}
    private fun init() {
        logz("FragSearch`Init")
        requestSubCategoryData(catID)
        requestProducts(subCatID)
    }
    private fun setupTabLayout(subCategories: ArrayList<SubCategory>) {
        var hasNotBeenSet = true
        for ( subCategory in subCategories) {
//            var selected = subCategory.subId == subCatID
            tab_layout.addTab(tab_layout.newTab().setText(subCategory.subName),hasNotBeenSet)
            hasNotBeenSet = false
        }
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) { navigateToTab(tab?.position ?: 0) }
        })
    }

    private fun navigateToTab(subID:Int) {
        val bundle = Bundle()
        bundle.putInt(KEY_CAT_ID, catID)
        bundle.putInt(KEY_SUB_CAT_ID, subID)
        val frag = FragSearch()
        frag.arguments = bundle
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.frame_fragments, frag)
            ?.setTransition(FragmentTransaction.TRANSIT_NONE)
            ?.commit()
    }

    private fun requestSubCategoryData(catID:Int) {
        val endpoint = Endpoints.getSelectedSubCategoriesEndpoint(catID+1)
        var requestQueue = Volley.newRequestQueue(context)
        var request = JsonObjectRequest(
            Request.Method.GET, endpoint, null,
            Response.Listener { response ->
                var gson = GsonBuilder().create()
                var subCategoriesObjectZ = gson.fromJson(response.toString(), ReceivedSubCategoriesObject::class.java)
                // give data to tab_layout
                setupTabLayout(subCategoriesObjectZ.data)
            },
            Response.ErrorListener {
                logz("requestSubCategoryData`Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }

    private fun requestProducts(subCatID:Int) {
        val endpoint = Endpoints.getSelectedProductsEndpoint(subCatID)
        var requestQueue = Volley.newRequestQueue(context)
        var request = JsonObjectRequest(
            Request.Method.GET, endpoint, null,
            Response.Listener { response ->
                var gson = GsonBuilder().create()
                var subCategoryData = gson.fromJson(response.toString(), ReceivedSubCategoriesObject::class.java)
                // give data to tab_layout adapter
//                var mAdapter = view_pager.adapter
//                if (mAdapter is AdapterSubCategories) {
//                    mAdapter.data = subCategoryData.data
//                }
            },
            Response.ErrorListener {
                Log.d("TMLog", "Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }


}
