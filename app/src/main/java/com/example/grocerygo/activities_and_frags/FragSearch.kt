package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.adapters.AdapterSubCategories
import com.example.grocerygo.R
import com.example.grocerygo.extras.*
import com.example.grocerygo.models.SubCategoryData
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_search.*
import kotlinx.android.synthetic.main.frag_search.view.*

class FragSearch : Fragment(), Title {
    override val title = "Search"
    private val viewPagerAdapter: AdapterSubCategories by lazy { AdapterSubCategories(activity!!.supportFragmentManager) }
    var bundle:Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = savedInstanceState
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_search,container,false)
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        // receive catID
        var catID = bundle?.getInt(KEY_CAT_ID, 0)
        //
        setupTabLayout()
        requestSubCategoryData(catID?:0)
    }
    private fun setupTabLayout() {
        view_pager.adapter = viewPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun requestSubCategoryData(selectedCatID:Int) {
        val endpoint = Endpoints.getSelectedSubCategoriesEndpoint(selectedCatID+1)
        var requestQueue = Volley.newRequestQueue(context)
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
