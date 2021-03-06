package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.activities_and_frags.Inheritables.ToolbarCallbacks
import com.example.grocerygo.adapters.AdapterRecyclerView
import com.example.grocerygo.extras.*
import com.example.grocerygo.models.Product
import com.example.grocerygo.models.received.ReceivedProductsObject
import com.example.tmcommonkotlin.logz
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.frag_search_products.*
import kotlinx.android.synthetic.main.includible_plus_minus.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class ActivitySearch : GGToolbarActivity(R.layout.activity_search), AdapterRecyclerView.Callbacks {
    override val title = "Search"
    lateinit var products: ArrayList<Product>
    override fun onStart() {
        super.onStart()
        setupListeners()
    }

    private fun setupListeners() {
        button_search.setOnClickListener {
            requestProductsBySearch(edit_text_search.text.toString())
        }
    }


    private fun requestProductsBySearch(s:String) {
        val endpoint = Endpoints.getSearchEndpoint(s)
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.GET, endpoint, null,
            Response.Listener { response ->
                var gson = GsonBuilder().create()
                var receivedProductsObject =
                    gson.fromJson(response.toString(), ReceivedProductsObject::class.java)
                // add quantities from db
                products = receivedProductsObject.data
                for (product in products) {
                    product.quantity = App.db.getProductQuantityByProductID(product._id)?:0
                }
                // give to AdapterProducts
                setupRecyclerView()
            },
            Response.ErrorListener {
                logz("Response.ErrorListener`it:$it")
            })
        requestQueue.add(request)
    }


    private fun setupRecyclerView() {
        recycler_view_search.layoutManager = LinearLayoutManager(this)
        recycler_view_search.adapter = AdapterRecyclerView(this, this, R.layout.item_product)
        recycler_view_search
            .addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    override fun bindRecyclerItemView(view: View, i: Int) {
        view.text_view_name.text = products[i].productName
        view.text_view_price.text = "$"+products[i].price.toString()
        view.text_view_fake_price.text = "$"+products[i].mrp.toString()
        view.text_view_fake_price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        view.image_view_product.easyPicasso(Endpoints.getImageEndpoint(products[i].image))
        //
        view.setOnClickListener {
            var intent = Intent(this, ActivityDetails::class.java)
            var product = products[i]
            intent.putExtra(KEY_PRODUCT, product)
            startActivity(intent)
        }
        // plus minus
        view.text_view_add.setOnClickListener {
            view.text_view_add.visibility= View.GONE
            App.db.addProduct(products[i])
            recycler_view_search.adapter?.notifyDataSetChanged()
            (this as ToolbarCallbacks).notifyCartBadge()
        }
        view.button_plus.setOnClickListener {
            App.db.addProduct(products[i])
            recycler_view_search.adapter?.notifyDataSetChanged()
            (this as ToolbarCallbacks).notifyCartBadge()
        }
        view.button_minus.setOnClickListener {
            if (products[i].quantity == 1) {
                products[i].quantity = 0 // to update ui
                view.text_view_add.visibility= View.VISIBLE
                App.db.deleteProduct(products[i])
            } else {
                App.db.minusProduct(products[i])
            }
            recycler_view_search.adapter?.notifyDataSetChanged()
            (this as ToolbarCallbacks).notifyCartBadge()
        }
        view.text_view_number_plus_minus.text = products[i].quantity.toString()
        if (products[i].quantity > 0) {
            view.text_view_add.visibility= View.GONE
        } else {
            view.text_view_add.visibility= View.VISIBLE
        }
    }

    override fun getRecyclerDataSize(): Int {
        return products.size
    }
}