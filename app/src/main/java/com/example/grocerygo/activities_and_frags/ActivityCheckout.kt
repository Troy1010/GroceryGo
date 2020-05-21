package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.inheritables.GGToolbarActivity
import kotlinx.android.synthetic.main.activity_c_order_review.*

class ActivityCheckout: GGToolbarActivity() {
    override val title: String
        get() = "Order Review"
    override val layout: Int
        get() = R.layout.activity_c_order_review

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_place_order.setOnClickListener {
            startActivity(Intent(this, ActivityThankYou::class.java))
        }
    }
}