package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterRecyclerView
import com.example.grocerygo.extras.*
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.models.Product
import com.example.grocerygo.models.OrderSummary
import kotlinx.android.synthetic.main.frag_order_review.*
import kotlinx.android.synthetic.main.item_order_review.view.*
import kotlinx.android.synthetic.main.z_cart_last_item.*

class ActivityOrderReview : GGToolbarActivity(layout = R.layout.activity_order_review) {
    override val title = "Order Review"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frag = FragOrderReview()
        supportFragmentManager.beginTransaction().replace(R.id.frame_frag_order_review, frag).commit()
    }
}