package com.example.grocerygo_jsonparsingandconfig.activities_and_frags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.adapters.AdapterSubCategories
import com.example.grocerygo_jsonparsingandconfig.R
import com.example.grocerygo_jsonparsingandconfig.extras.Endpoints
import com.example.grocerygo_jsonparsingandconfig.models.SubCategoryData
import com.example.grocerygo_jsonparsingandconfig.models.Product
import com.example.grocerygo_jsonparsingandconfig.models.SubCategory
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_content.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        init()
    }

    private fun init() {
        var i = intent.getIntExtra("CATEGORYINDEX", 0)
        Log.d("TMLog", "ActivityContent`init`index:${i}")
        view_pager.adapter = AdapterSubCategories(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
//        tab_layout.tabMode = TabLayout.MODE_SCROLLABLE

        requestSubCategoryData(i)

    }

    private fun requestSubCategoryData(selectedCatID:Int) {
        val endpoint = Endpoints.getSelectedSubCategoriesEndpoint(selectedCatID)
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
