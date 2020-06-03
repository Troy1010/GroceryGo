package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterRecyclerView
import com.example.grocerygo.extras.App
import com.example.grocerygo.extras.DisplayMoney
import com.example.grocerygo.extras.Requester
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.models.Order
import com.example.grocerygo.models.OrderSummary
import com.example.grocerygo.models.Product
import com.example.grocerygo.models.received.ReceivedOrdersObject
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.item_order_history.view.*

class ActivityOrderHistory : GGToolbarActivity(layout = R.layout.activity_order_history), AdapterRecyclerView.Callbacks {
    override val title = "Order History"
    var orders = ArrayList<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
        Requester.requestOrders(App.sm.user?._id,
            Response.Listener {
                val receivedOrdersObject = GsonBuilder().create()
                    .fromJson(it.toString(), ReceivedOrdersObject::class.java)
                //
                orders = ArrayList(receivedOrdersObject.data)
                recycler_view_order_history.adapter?.notifyDataSetChanged()
                //
                refresh()
            }
        )
        refresh()
    }


    private fun refresh() {
        if (orders.size==0) {
            text_view_order_history_is_empty.visibility = View.VISIBLE
        } else {
            text_view_order_history_is_empty.visibility = View.GONE
        }
    }



    private fun setupRecyclerView() {
        recycler_view_order_history.layoutManager = LinearLayoutManager(this)
        recycler_view_order_history.adapter =
            AdapterRecyclerView(this, this, R.layout.item_order_history)
        recycler_view_order_history
            .addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    override fun bindRecyclerItemView(view: View, i: Int) {
        val orderSummary = OrderSummary(orders[i].products)
        view.text_view_date_value.text = orders[i].date?.take(10)
        view.text_view_quantity_value.text = "${orderSummary.totalQuantity} item(s)"
        view.text_view_grand_total_value.text = DisplayMoney(orderSummary.grandTotal)
        view.setOnClickListener {
            val intent = Intent(this, ActivityOrderHistoryItemDetails::class.java)
            val products = orders[i].products as ArrayList<Product>
            intent.putExtra(ActivityOrderHistoryItemDetails.KEY_PRODUCTS, products)
            intent.putExtra(ActivityOrderHistoryItemDetails.KEY_DATE, orders[i].date)
            startActivity(intent)
        }
    }

    override fun getRecyclerDataSize(): Int {
        return orders.size
    }
}
