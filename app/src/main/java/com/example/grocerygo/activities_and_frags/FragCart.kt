package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.HostCallbacks
import com.example.grocerygo.activities_and_frags.Inheritables.TMFragment
import com.example.grocerygo.activities_and_frags.Inheritables.ToolbarCallbacks
import com.example.grocerygo.adapters.CustomAdapterCart
import com.example.grocerygo.extras.*
import com.example.grocerygo.models.OrderSummary
import com.example.grocerygo.models.Product
import kotlinx.android.synthetic.main.frag_cart.*
import kotlinx.android.synthetic.main.includible_plus_minus.view.*
import kotlinx.android.synthetic.main.item_cart_item.view.*
import kotlinx.android.synthetic.main.z_cart_last_item.view.*

class FragCart : TMFragment(layout = R.layout.frag_cart), CustomAdapterCart.Callbacks {
    override var products = arrayListOf<Product>()
    lateinit var orderSummary: OrderSummary
    lateinit var layoutManager: LinearLayoutManager

    override fun onStart() {
        super.onStart()
        setupParent()
        setupAdapter()
        setupListeners()
        refresh()
    }

    private fun setupListeners() {
        button_checkout.setOnClickListener {
            if (App.sm.user == null) {
                App.sm.goToPaymentInfoAfterLogin = true
                val intent = Intent(activity!!, ActivityHost::class.java)
                intent.putExtra(ActivityHost.KEY_TAB_ID, ActivityHost.TabEnum.Profile.id)
                startActivity(intent)
            } else if (products.isEmpty()) {
                this.easyToast("Must have at least one item")
            } else {
                startActivity(Intent(activity!!, ActivityPaymentInfo::class.java))
            }
        }
    }


    private fun setupParent() {
        (activity as HostCallbacks).showNavigationBar(false)
        (activity as ToolbarCallbacks).showCart(false)
        (activity as ToolbarCallbacks).showBack(true)
        (activity as ToolbarCallbacks).setTitle("Cart")
    }

    private fun setupAdapter() {
        layoutManager = GridLayoutManager(activity!!, 1)
        recycler_view_cart_items.layoutManager = layoutManager
        recycler_view_cart_items.adapter = CustomAdapterCart(this, activity!!)
        recycler_view_cart_items
            .addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
    }

    fun refresh() {
        products = App.db.getProducts()
        if (products.size == 0) {
            text_view_cart_is_empty.visibility = View.VISIBLE
            recycler_view_cart_items.visibility = View.GONE
            text_view_est_total_value.visibility = View.GONE
            text_view_est_total_title.visibility = View.GONE
        } else {
            text_view_cart_is_empty.visibility = View.GONE
            recycler_view_cart_items.visibility = View.VISIBLE
            text_view_est_total_value.visibility = View.VISIBLE
            text_view_est_total_title.visibility = View.VISIBLE
        }
        recycler_view_cart_items.adapter?.notifyDataSetChanged()
        orderSummary = OrderSummary(products)
        (activity as ToolbarCallbacks).notifyCartBadge()
        text_view_est_total_value.text = DisplayMoney(orderSummary.grandTotal)
    }

    override fun bindRecyclerItemView(view: View, i: Int) {
        view.text_view_name.text = products[i].productName
        view.text_view_price.text = "$" + products[i].price.toString()
        view.button_trash.setOnClickListener {
            App.db.deleteProduct(products[i])
            refresh()
        }
        view.button_plus.setOnClickListener {
            App.db.addProduct(products[i])
            refresh()
        }
        view.button_minus.setOnClickListener {
            App.db.minusProduct(products[i])
            refresh()
        }
        view.text_view_number_plus_minus.text = products[i].quantity.toString()
        view.text_view_add.visibility = View.GONE
        view.image_view_product.easyPicasso(Endpoints.getImageEndpoint(products[i].image))
    }

    override fun bindLastRecyclerItemView(view: View, normalItemHeight: Int?) {
        view.layoutParams.height =
            max(400, layoutManager.height - (normalItemHeight ?: 0) * products.size - 200)

        if (products.size != 0) {
            view.text_view_item_quantity.text = "${orderSummary.totalQuantity} item(s)"
            view.text_view_fake_price_total.text = DisplayMoney(orderSummary.totalFakePrice)
            view.text_view_fake_price_total.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            view.text_view_fake_discount.text =
                "${DisplayMoney(orderSummary.fakeDiscount)} discount (${orderSummary.fakeDiscountPercentage}%)"
            view.text_view_price_total.text = DisplayMoney(orderSummary.totalPrice)
            view.text_view_tax.text = DisplayMoney(orderSummary.tax)
            view.text_view_shipping.text = DisplayMoney(orderSummary.deliveryFee)
        }
    }


}
