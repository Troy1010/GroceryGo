package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.example.grocerygo.R
import com.example.grocerygo.inheritables.GGToolbarActivity
import kotlinx.android.synthetic.main.activity_c_payment_info.*

class ActivityPaymentInfo: GGToolbarActivity() {
    override val title: String
        get() = "Payment Info"
    override val layout: Int
        get() = R.layout.activity_c_payment_info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_payment_info_send.setOnClickListener {
            startActivity(Intent(this, ActivityOrderReview::class.java))
        }
        frame_address.setOnClickListener {
            startActivity(Intent(this, ActivityAddress::class.java))
        }
        frame_payment.setOnClickListener {
            startActivity(Intent(this, ActivityPayment::class.java))
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.menu_cart).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }
}