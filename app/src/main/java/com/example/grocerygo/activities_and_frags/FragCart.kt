package com.example.grocerygo.activities_and_frags

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterCartItems
import com.example.grocerygo.extras.*
import com.example.grocerygo.inheritables.ActivityHostCallbacks
import com.example.grocerygo.inheritables.GGActivityCallbacks
import com.example.grocerygo.inheritables.RecyclerViewActivityCallbacks
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.frag_cart.*
import kotlinx.android.synthetic.main.includible_plus_minus.view.*
import kotlinx.android.synthetic.main.item_cart_item.view.*

class FragCart : TMFragment(), RecyclerViewActivityCallbacks {
    override val layout = R.layout.frag_cart
    val title = "Cart"
    lateinit var products:ArrayList<Product>

    override fun onStart() {
        super.onStart()
        (activity as GGActivityCallbacks).setToolbarAttributes(title, true)
        (activity as ActivityHostCallbacks).setNavigationEmpty(true)
        setupAdapter()
        refresh()
    }

    private fun setupAdapter() {
        recycler_view_cart_items.layoutManager = LinearLayoutManager(activity!!)
        recycler_view_cart_items.adapter = AdapterCartItems(this, activity!!, ArrayList())
        recycler_view_cart_items
            .addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
    }

    fun refresh() {
        products = App.db.getProducts()
        val orderSummary = App.db.getOrderSummary()
        (recycler_view_cart_items.adapter as AdapterCartItems).products = products // TODO don't pass into Adapter
        (recycler_view_cart_items.adapter as AdapterCartItems).notifyDataSetChanged()
        if ((recycler_view_cart_items.adapter as AdapterCartItems).products.size==0) {
            text_view_cart_is_empty.visibility = View.VISIBLE
            text_view_money_total.visibility = View.INVISIBLE
            text_view_list_size.visibility = View.INVISIBLE
        } else {
            text_view_list_size.text = "# of items: ${orderSummary.quantityTotal}"
            text_view_money_total.text = "total: ${orderSummary.priceTotal}"
        }
    }

    override fun bindRecyclerItemView(v: View, i: Int) {
        v.text_view_name.text = products[i].productName
        v.text_view_price.text = "$"+products[i].price.toString()
        v.button_trash.setOnClickListener {
            App.db.deleteProduct(products[i])
            refresh()
        }
        v.button_plus.setOnClickListener {
            App.db.addProduct(products[i])
            refresh()
        }
        v.button_minus.setOnClickListener {
            App.db.minusProduct(products[i])
            refresh()
        }
        v.text_view_number_plus_minus.text = products[i].quantity.toString()
        v.text_view_add.visibility=View.GONE
        // Picasso image
        val imageEndpoint = Endpoints.getImageEndpoint(products[i].image)
        Picasso
            .get()
            .load(imageEndpoint)
            .placeholder(R.drawable.not_found)
            .error(R.drawable.no_image_available_vector_illustration_260nw_744886198)
            .into(v.image_view_product)
    }


}
