package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterRecyclerView
import com.example.grocerygo.extras.*
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.models.Product
import com.example.grocerygo.models.OrderSummary_PASSABLE
import com.example.grocerygo.models.Order
import com.example.grocerygo.models.OrderSummary
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_c_order_review.*
import kotlinx.android.synthetic.main.item_order_review.view.*
import kotlinx.android.synthetic.main.z_cart_last_item.*
import kotlinx.android.synthetic.main.z_cart_last_item.view.*
import kotlinx.android.synthetic.main.z_cart_last_item.view.text_view_fake_discount
import kotlinx.android.synthetic.main.z_cart_last_item.view.text_view_fake_price_total
import kotlinx.android.synthetic.main.z_cart_last_item.view.text_view_item_quantity
import kotlinx.android.synthetic.main.z_cart_last_item.view.text_view_price_total
import kotlinx.android.synthetic.main.z_cart_last_item.view.text_view_shipping
import kotlinx.android.synthetic.main.z_cart_last_item.view.text_view_tax
import org.json.JSONObject

class ActivityOrderReview : GGToolbarActivity(layout = R.layout.activity_c_order_review), AdapterRecyclerView.Callbacks {
    override val title = "Order Review"
    lateinit var products: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupClickListeners()
        refresh()
        setupRecyclerView()
    }

    private fun refresh() {
        text_view_profile_value.text = App.sm.user?.name ?: "!WARNING: User not logged in"
        text_view_address_value.text = App.sm.primaryAddress?.displayableStreetAddress ?: "!WARNING: Primary address not selected"
        text_view_payment_value.text = App.sm.displayPayment?: "!WARNING: Payment method not selected"
        //
        products = App.db.getProducts()
        val orderSummary = OrderSummary(products)
        text_view_item_quantity.text = "${orderSummary.totalQuantity} item(s)"
        text_view_fake_price_total.text = DisplayMoney(orderSummary.totalFakePrice)
        text_view_fake_price_total.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        text_view_fake_discount.text =
            "${DisplayMoney(orderSummary.fakeDiscount)} discount (${orderSummary.fakeDiscountPercentage}%)"
        text_view_price_total.text = DisplayMoney(orderSummary.totalPrice)
        text_view_tax.text = DisplayMoney(orderSummary.tax)
        text_view_shipping.text = DisplayMoney(orderSummary.deliveryFee)
        text_view_grand_total_value.text = DisplayMoney(OrderSummary(App.db.getProducts()).grandTotal)
    }

    private fun setupClickListeners() {
        button_place_order.setOnClickListener {
            Requester.requestOrderPlacement(Response.Listener {
                App.db.clear()
            })
            val intent = Intent(this, ActivityThankYou::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    // Setup Toolbar
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.menu_cart).isVisible = false
        return super.onPrepareOptionsMenu(menu) // TODO replace with new way of doing this
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