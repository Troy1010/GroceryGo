package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterCart
import com.example.grocerygo.adapters.AdapterRecyclerView
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.DisplayMoney
import com.example.grocerygo.extras.Endpoints
import com.example.grocerygo.extras.easyPicasso
import com.example.grocerygo.inheritables.GGToolbarActivity
import com.example.grocerygo.inheritables.RecyclerViewCallbacks
import com.example.grocerygo.models.Product
import kotlinx.android.synthetic.main.activity_c_order_review.*
import kotlinx.android.synthetic.main.frag_cart.*
import kotlinx.android.synthetic.main.item_order_review.view.*

class ActivityOrderReview : GGToolbarActivity(), RecyclerViewCallbacks {
    override val title: String
        get() = "Order Review"
    override val layout: Int
        get() = R.layout.activity_c_order_review
    lateinit var products: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupClickListeners()
        refresh()
        setupRecyclerView()
    }

    private fun setupClickListeners() {
        button_place_order.setOnClickListener {
            val intent = Intent(this, ActivityThankYou::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    // Setup Toolbar
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.menu_cart).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    private fun refresh() {
        products = App.db.getProducts()
        text_view_grand_total_value.text = DisplayMoney(App.db.getOrderSummary().getGrandTotal())
    }

    private fun setupRecyclerView() {
        recycler_view_order_review.layoutManager = LinearLayoutManager(this)
        recycler_view_order_review.adapter =
            AdapterRecyclerView(this, this, R.layout.item_order_review)
        recycler_view_order_review
            .addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    override fun bindRecyclerItemView(view: View, i: Int) {
        val product = products[i]
        view.text_view_name.text = product.productName
        view.text_view_quantity_price.text = "$${product.price * product.quantity}"
        view.text_view_quantity.text = "Qty: ${product.quantity}"
        view.image_view_product.easyPicasso(Endpoints.getImageEndpoint(product.image))
    }

    override fun getRecyclerDataSize(): Int {
        return products.size
    }
}