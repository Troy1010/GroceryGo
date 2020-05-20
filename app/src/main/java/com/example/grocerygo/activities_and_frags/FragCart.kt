package com.example.grocerygo.activities_and_frags

import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterRecyclerView
import com.example.grocerygo.extras.*
import com.example.grocerygo.inheritables.*
import com.example.grocerygo.models.Product
import kotlinx.android.synthetic.main.frag_cart.*
import kotlinx.android.synthetic.main.includible_plus_minus.view.*
import kotlinx.android.synthetic.main.item_cart_item.view.*

class FragCart : GGFragment(), RecyclerViewActivityCallbacks {
    override val layout = R.layout.frag_cart
    lateinit var products:ArrayList<Product>
    override val title = "Cart"

    override fun onStart() {
        super.onStart()
        (activity as ActivityHostCallbacks).setNavigationEmpty(true)
        setupAdapter()
        refresh()
    }

    private fun setupAdapter() {
        recycler_view_cart_items.layoutManager = LinearLayoutManager(activity!!)
        recycler_view_cart_items.adapter = AdapterRecyclerView(this, activity!!, R.layout.item_cart_item)
        recycler_view_cart_items
            .addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
    }

    fun refresh() {
        products = App.db.getProducts()
        (activity as GGToolbarActivityCallbacks).notifyBadge()
        val orderSummary = App.db.getOrderSummary()
        recycler_view_cart_items.adapter?.notifyDataSetChanged()
        if (products.size==0) {
            text_view_cart_is_empty.visibility = View.VISIBLE
            text_view_price_total.visibility = View.INVISIBLE
            text_view_quantity_total.visibility = View.INVISIBLE
            text_view_fake_price_total.visibility = View.INVISIBLE
            text_view_discount.visibility = View.INVISIBLE
            text_view_delivery_fee.visibility = View.INVISIBLE
            text_view_final_price.visibility = View.INVISIBLE
        } else {
            text_view_quantity_total.text = "# of items: ${orderSummary.quantityTotal}"
            text_view_fake_price_total.text = "fake total: ${orderSummary.fakePriceTotal}"
            text_view_fake_price_total.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            text_view_price_total.text = "total: ${orderSummary.priceTotal}"
            text_view_discount.text = "Discount: ${orderSummary.getDiscount()}"
            text_view_delivery_fee.text = "Delivery Fee: ${orderSummary.getDeliveryFee()}"
            text_view_final_price.text = "Final Price: ${orderSummary.getGrandTotal()}"
        }

    }

    override fun bindRecyclerItemView(view: View, i: Int) {
        view.text_view_name.text = products[i].productName
        view.text_view_price.text = "$"+products[i].price.toString()
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
        view.text_view_add.visibility=View.GONE
        view.image_view_category.easyPicasso(Endpoints.getImageEndpoint(products[i].image))
    }

    override fun getRecyclerDataSize():Int {
        return App.db.getProducts().size
    }


}
