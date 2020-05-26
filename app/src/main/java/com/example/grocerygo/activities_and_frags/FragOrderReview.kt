package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.HostCallbacks
import com.example.grocerygo.activities_and_frags.Inheritables.TMFragment
import com.example.grocerygo.activities_and_frags.Inheritables.ToolbarCallbacks
import com.example.grocerygo.adapters.AdapterRecyclerView
import com.example.grocerygo.extras.*
import com.example.grocerygo.models.OrderSummary
import com.example.grocerygo.models.Product
import kotlinx.android.synthetic.main.frag_order_review.*
import kotlinx.android.synthetic.main.item_order_review.view.*
import kotlinx.android.synthetic.main.z_cart_last_item.*

class FragOrderReview(val products:ArrayList<Product>) : TMFragment(R.layout.frag_order_review), AdapterRecyclerView.Callbacks {

    override fun onCreateViewInit() {
        setupParent()
        super.onCreateViewInit()
    }

    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        setupClickListeners()
        refresh()
        setupParent()
    }


    private fun setupParent() {
        (activity as ToolbarCallbacks).showCart(false)
        (activity as ToolbarCallbacks).showBack(true)
        (activity as ToolbarCallbacks).setTitle("Order Review")
    }

    private fun refresh() {
        text_view_profile_value.text = App.sm.user?.name ?: "!WARNING: User not logged in"
        text_view_address_value.text = App.sm.primaryAddress?.displayableStreetAddress ?: "!WARNING: Primary address not selected"
        text_view_payment_value.text = App.sm.displayPayment?: "!WARNING: Payment method not selected"
        //
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
            val intent = Intent(activity!!, ActivityThankYou::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        recycler_view_order_review.layoutManager = LinearLayoutManager(activity!!)
        recycler_view_order_review.adapter =
            AdapterRecyclerView(this, activity!!, R.layout.item_order_review)
        recycler_view_order_review
            .addItemDecoration(DividerItemDecoration(activity!!, RecyclerView.VERTICAL))
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