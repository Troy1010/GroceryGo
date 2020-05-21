package com.example.grocerygo.activities_and_frags

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterRecyclerView
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.extras.easyPicasso
import com.example.grocerygo.extras.logz
import com.example.grocerygo.inheritables.*
import com.example.grocerygo.models.Category
import com.example.grocerygo.models.ReceivedCategoriesObject
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_search_categories.*
import kotlinx.android.synthetic.main.item_category.view.*

class FragSearchCategories : GGFragment(), RecyclerViewCallbacks {
    override val title = "Search"
    override val layout: Int
        get() = R.layout.frag_search_categories
    lateinit var categories:ArrayList<Category>

    override fun onCreateViewInit() {
        requestCategories()
    }

    override fun onStart() {
        super.onStart()
        (activity as HostCallbacks).setNavigationEmpty(false)
        (activity as GGToolbarActivity).toolbarMenu?.findItem(R.id.menu_cart)?.isVisible = true
    }

    private fun setupRecyclerView() {
        recycler_view_categories?.layoutManager = GridLayoutManager(activity!!,2)
        recycler_view_categories?.adapter = AdapterRecyclerView(this, activity!!, R.layout.item_category)
    }

    private fun requestCategories() {
        var requestQueue = Volley.newRequestQueue(activity!!)
        var request = StringRequest(
            Request.Method.GET, Endpoints.vCategoryEndpoint,
            Response.Listener { response ->
                var receivedCategoriesObject = GsonBuilder().create()
                    .fromJson(response, ReceivedCategoriesObject::class.java)
                categories = receivedCategoriesObject.data
                setupRecyclerView()
            },
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }

    override fun bindRecyclerItemView(view: View, i: Int) {
        view.text_view.text = categories[i].catName
        view.image_view_category.easyPicasso(Endpoints.getImageEndpoint(categories[i].catImage))
        view.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_fragments, FragSearchToolbar.newInstance(categories[i].catId))
                ?.addToBackStack(null) // is null okay?
                ?.commit()
        }
    }

    override fun getRecyclerDataSize(): Int {
        return categories.size
    }
}