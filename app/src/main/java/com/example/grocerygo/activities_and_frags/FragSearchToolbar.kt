package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.extras.KEY_CAT_ID
import com.example.grocerygo.extras.logz
import com.example.grocerygo.inheritables.HostCallbacks
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.inheritables.ToolbarCallbacks
import com.example.grocerygo.models.received.ReceivedSubCategoriesObject
import com.example.grocerygo.models.SubCategory
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_search_toolbar.*

class FragSearchToolbar : TMFragment() {
    override val layout: Int
        get() = R.layout.frag_search_toolbar
    val catID by lazy { arguments?.getInt(KEY_CAT_ID)?:1 }

    override fun onStart() {
        super.onStart()
        setupParent()
    }

    private fun setupParent() {
        (activity as HostCallbacks).showNavigationBar(true)
        (activity as ToolbarCallbacks).showCart(true)
        (activity as ToolbarCallbacks).showBack(true)
        (activity as ToolbarCallbacks).setTitle("Login")
    }

    override fun onCreateViewInit() {
        super.onCreateViewInit()
        requestSubCategoryData(catID) // TODO make other requests run by onCreateView b/c com.google.android.material.tabs.TabLayout was null
    }

    private fun setupTabLayout(subCategories: ArrayList<SubCategory>) {
        var noneAreYetSelected = true
        for (subCategory in subCategories) {
            tab_layout.addTab(tab_layout.newTab().setText(subCategory.subName), noneAreYetSelected)
            noneAreYetSelected = false
        }
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab) {
                navigateToTab(subCategories[tab.position].subId)
            }
        })
    }

    private fun navigateToTab(subCatID: Int) {
        // remove all fragments that aren't me
        var activityZ = activity
        activityZ?.apply {
            for (frag:Fragment in activityZ.supportFragmentManager.fragments) {
                if (frag !is FragSearchToolbar) {
                    activityZ.supportFragmentManager
                        .beginTransaction()
                        .remove(frag)
                        .commit()
                }
            }
        }
        // add fragment with new products by subCatID
        activityZ?.apply {
            activityZ.supportFragmentManager
                .beginTransaction()
                .add(R.id.frame_fragments, FragSearchProducts.newInstance(subCatID=subCatID))
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .commit()
        }

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
                // open products
                if (subCategoriesObjectZ.data.size > 0) {
                    navigateToTab(subCategoriesObjectZ.data[0].subId)
                }
            },
            Response.ErrorListener {
                logz("requestSubCategoryData`Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(catID: Int) =
            FragSearchToolbar().apply {
                arguments = Bundle().apply {
                    putInt(KEY_CAT_ID, catID)
                }
            }
    }
}