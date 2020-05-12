package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.adapters.AdapterSubCategories
import com.example.grocerygo.R
import com.example.grocerygo.extras.*
import com.example.grocerygo.models.SubCategoryData
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_sub_cats_and_products.*
import kotlinx.android.synthetic.main.app_toolbar.*

class ActivityWithProducts : AppCompatActivityWithToolbarFunctionality() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_cats_and_products)
        init()
    }

    private fun init() {
        var catID = intent.getIntExtra(KEY_CAT_ID, 0)
        Log.d("TMLog", "ActivitySubCatsAndProducts`init`index:${catID}")
        view_pager.adapter = AdapterSubCategories(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
        requestSubCategoryData(catID)
        toolbar_top.setup(this, intent.getStringExtra(KEY_SUB_TITLE) ?: "<No Title>")
    }

    private fun requestSubCategoryData(selectedCatID:Int) {
        val endpoint = Endpoints.getSelectedSubCategoriesEndpoint(selectedCatID+1)
        Log.d("TMLog","requestSubCategoryData`endpoint:$endpoint")
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.GET, endpoint, null,
            Response.Listener { response ->
                var gson = GsonBuilder().create()
                var subCategoryData = gson.fromJson(response.toString(), SubCategoryData::class.java)
                // give data to adapter
                var mAdapter = view_pager.adapter
                if (mAdapter is AdapterSubCategories) {
                    mAdapter.data = subCategoryData.data
                }
            },
            Response.ErrorListener {
                Log.d("TMLog", "Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }


}
