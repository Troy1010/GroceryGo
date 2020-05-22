package com.example.grocerygo.activities_and_frags

import androidx.appcompat.app.AppCompatActivity
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
import com.example.grocerygo.extras.logz
import com.example.grocerygo.inheritables.GGToolbarActivity
import com.example.grocerygo.models.Order
import com.example.grocerygo.models.received.ReceivedOrdersObject
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_c_order_review.*
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.item_order_history.view.*

class ActivityOrderHistory : GGToolbarActivity(), AdapterRecyclerView.Callbacks {
    override val title: String
        get() = "Order History"
    override val layout: Int
        get() = R.layout.activity_order_history
    var orders = ArrayList<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
    }


    override fun onStart() {
        super.onStart()
        refresh()
    }


    private fun refresh() {
        Requester.requestOrders(App.sm.user._id,
            Response.Listener {
                val receivedOrdersObject = GsonBuilder().create()
                    .fromJson(it.toString(), ReceivedOrdersObject::class.java)
                //
                orders = ArrayList(receivedOrdersObject.data)
                logz(orders.toString())
                recycler_view_order_history.adapter?.notifyDataSetChanged()
            }
        )
    }



    private fun setupRecyclerView() {
        recycler_view_order_history.layoutManager = LinearLayoutManager(this)
        recycler_view_order_history.adapter =
            AdapterRecyclerView(this, this, R.layout.item_order_history)
        recycler_view_order_history
            .addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    override fun bindRecyclerItemView(view: View, i: Int) {
        view.text_view_quantity_value.text = orders[i].products.size.toString()
        view.text_view_grand_total_title.text = DisplayMoney(orders[i].orderSummary.totalAmount)
        view.text_view_date_value.text = orders[i].date
    }

    override fun getRecyclerDataSize(): Int {
        return orders.size
    }
}
