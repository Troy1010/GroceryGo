package com.example.grocerygo.activities_and_frags

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterCartItems
import com.example.grocerygo.extras.*
import com.example.grocerygo.inheritables.GGToolbarActivity
import com.example.grocerygo.inheritables.TMFragment
import com.example.grocerygo.models.Product
import kotlinx.android.synthetic.main.frag_cart.*

class FragCart : TMFragment() {
    override val layout = R.layout.frag_cart
    val title = "Cart"

    override fun onStart() {
        super.onStart()
        val activityZ = activity
        if (activityZ is GGToolbarActivity) {
            activityZ.setToolbarTitle(this.title)
        }
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

    fun refresh() {
        (recycler_view_cart_items.adapter as AdapterCartItems).products = App.db.getProducts()
        (recycler_view_cart_items.adapter as AdapterCartItems).notifyDataSetChanged()
        // # of items
        val products = App.db.getProducts()
        text_view_list_size.text = "# of items: ${products.size.toString()}"
        // get money total
        var moneyTotal = 0.0
        for (product in products) {
            moneyTotal += product.price
        }
        text_view_money_total.text = "total: ${moneyTotal.toString()}"
    }


}
