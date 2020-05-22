package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.adapters.CustomAdapterCart
import com.example.grocerygo.extras.*
import com.example.grocerygo.inheritables.*
import com.example.grocerygo.models.Product
import kotlinx.android.synthetic.main.frag_cart.*
import kotlinx.android.synthetic.main.includible_plus_minus.view.*
import kotlinx.android.synthetic.main.item_cart_item.view.*

class FragCart : GGFragment(), CustomAdapterCart.Callbacks {
    override val layout = R.layout.frag_cart
    override var products = arrayListOf<Product>()
    override val title = "Cart"
    lateinit var layoutManager: LinearLayoutManager

    override fun onStart() {
        super.onStart()
        (activity as HostCallbacks).setNavigationEmpty(true)
        (activity as ToolbarCallbacks).toolbarMenu?.findItem(R.id.menu_cart)?.isVisible = false
        setupAdapter()
        refresh()
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
        (activity as ToolbarCallbacks).notifyBadge()
        val orderSummary = App.db.getOrderSummary()
        recycler_view_cart_items.adapter?.notifyDataSetChanged()
        button_checkout.setOnClickListener {
            startActivity(Intent(activity!!, ActivityPaymentInfo::class.java))
        }
//        if (products.size==0) {
//            text_view_cart_is_empty.visibility = View.VISIBLE
//            text_view_item_quantity.visibility = View.INVISIBLE
//            text_view_price_total.visibility = View.INVISIBLE
//            text_view_fake_price_total.visibility = View.INVISIBLE
////            text_view_discount.visibility = View.INVISIBLE
//            text_view_shipping.visibility = View.INVISIBLE
//            text_view_est_total.visibility = View.INVISIBLE
//        } else {
//            text_view_coupon_discount.text = "- ${orderSummary.getCouponDiscount()}"
//            text_view_item_quantity.text = "${orderSummary.quantityTotal} Item(s)"
//            text_view_fake_price_total.text = "${orderSummary.fakePriceTotal}"
//            text_view_fake_price_total.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//            text_view_price_total.text = "total: ${orderSummary.priceTotal}"
//            text_view_fake_discount.text = "- ${orderSummary.getFakeDiscount()}"
//            text_view_tax.text = "${orderSummary.getTax()}"
//            text_view_shipping.text = "${orderSummary.getDeliveryFee()}"
//            text_view_est_total.text = "${orderSummary.getGrandTotal()}"
//        }

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
        view.image_view_product.easyPicasso(Endpoints.getImageEndpoint(products[i].image))
    }

    override fun bindLastRecyclerItemView(view: View, normalItemHeight: Int) {

        logz("```layoutManager.height:${layoutManager.height}")
        logz("```view.height:${view.height}")
        view.layoutParams.height = max(400,layoutManager.height - normalItemHeight*products.size - 200)
        logz("text_view_est_total_2.height:${text_view_est_total_2.height}")
        logz("button_checkout.height:${button_checkout.height}")
        logz("recycler_view_cart_items.height:${recycler_view_cart_items.height}")
    }


}
