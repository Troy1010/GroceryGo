package com.example.grocerygo.activities_and_frags

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterCartItems
import com.example.grocerygo.extras.*
import com.example.grocerygo.inheritables.GGActivityCallbacks
import com.example.grocerygo.inheritables.GGToolbarActivity
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.models.Product
import kotlinx.android.synthetic.main.frag_cart.*

class FragCart : TMFragment() {
    override val layout = R.layout.frag_cart
    val title = "Cart"

    override fun onStart() {
        super.onStart()
        (activity as GGActivityCallbacks).setToolbarAttributes(title, true)
        setupAdapter()
        refresh()
    }

    private fun setupAdapter() {
        recycler_view_cart_items.layoutManager = LinearLayoutManager(activity!!)
        recycler_view_cart_items.adapter = AdapterCartItems(this, activity!!, ArrayList())
        recycler_view_cart_items
            .addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
    }

    fun deleteProduct(product: Product) {
        App.db.deleteProduct(product)
        refresh()
    }

    fun addProduct(product: Product) {
        App.db.addProduct(product)
        refresh()
    }
    fun minusProduct(product: Product) {
        App.db.minusProduct(product)
        refresh()
    }

    fun refresh() {
        (recycler_view_cart_items.adapter as AdapterCartItems).products = App.db.getProducts() // TODO do I have to do this every refresh..?
        (recycler_view_cart_items.adapter as AdapterCartItems).notifyDataSetChanged()
        if ((recycler_view_cart_items.adapter as AdapterCartItems).products.size==0) {
            text_view_cart_is_empty.visibility = View.VISIBLE
            text_view_money_total.visibility = View.INVISIBLE
            text_view_list_size.visibility = View.INVISIBLE
        } else {
            // # of items
            val products = App.db.getProducts()
            text_view_list_size.text = "# of items: ${products.size}"
            // get money total
            var moneyTotal = 0.0
            for (product in products) {
                moneyTotal += product.price * product.quantity
            }
            text_view_money_total.text = "total: ${moneyTotal}"
        }
    }


}
