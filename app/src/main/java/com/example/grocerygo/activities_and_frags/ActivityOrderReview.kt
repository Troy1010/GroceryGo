package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.inheritables.GGToolbarActivity
import kotlinx.android.synthetic.main.activity_c_order_review.*

class ActivityOrderReview : GGToolbarActivity() {
    override val title: String
        get() = "Order Review"
    override val layout: Int
        get() = R.layout.activity_c_order_review

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_place_order.setOnClickListener {
            val intent = Intent(this, ActivityThankYou::class.java)
            intent.flags = FLAG_ACTIVITY_CLEAR_TASK // TODO non-functional
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler_view_order_review
    }
}