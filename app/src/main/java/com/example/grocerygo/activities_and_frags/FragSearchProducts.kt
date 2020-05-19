package com.example.grocerygo.activities_and_frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.grocerygo.inheritables.GGActivityCallbacks
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.models.Product
import com.example.grocerygo.models.ReceivedProductsObject
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.frag_search_products.recycler_view_products

class FragSearchProducts : TMFragment() {
    val title = "Search"
    val subCatID by lazy { arguments?.getInt(KEY_SUB_CAT_ID)?:1 }
    override val layout: Int
        get() = R.layout.frag_search_products

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestProducts(subCatID)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        (activity as GGActivityCallbacks).setToolbarAttributes(title, true)
    }

    private fun setupRecyclerView(products: ArrayList<Product>) {
        for (product in products) {
            product.quantity = App.db.getProductQuantityByProductID(product._id)?:0
        }
        recycler_view_products.layoutManager = LinearLayoutManager(activity!!)
        recycler_view_products.adapter = AdapterProducts(activity!!, products)
        recycler_view_products
            .addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
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
                // get quantities from our own DB
//                logz("get quantites from our own DB..")
//                for (productFromSql in App.db.getProducts())
//                {
//                    for (productFromRequest in receivedProductsObject.data) {
//                        if (productFromSql._id == productFromRequest._id) {
//                            logz("SUCCESS: productFromSql._id:${productFromSql._id} vs ${productFromRequest._id}")
//                            productFromRequest.quantity = productFromSql.quantity
//                        } else {
//                            logz("FAIL: productFromSql._id:${productFromSql._id} vs ${productFromRequest._id}")
//                        }
//                    }
//                }
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
        fun newInstance(subCatID: Int = 0) =
            FragSearchProducts().apply {
                arguments = Bundle().apply {
                    putInt(KEY_SUB_CAT_ID, subCatID)
                }
            }
    }


}
