package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.inheritables.GGToolbarActivity
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_payment_info.*

class ActivityPaymentInfo: GGToolbarActivity() {
    override val title: String
        get() = "Payment Info"
    override val layout: Int
        get() = R.layout.activity_payment_info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_payment_info_send.setOnClickListener {
            startActivity(Intent(this, ActivityCheckout::class.java))
        }
    }
}