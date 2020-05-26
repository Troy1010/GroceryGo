package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import kotlinx.android.synthetic.main.activity_payment.*

class ActivityPayment: GGToolbarActivity(layout = R.layout.activity_payment) {
    override val title: String
        get() = "Payment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_payment_submit.setOnClickListener {
            startActivity(Intent(this, ActivityPaymentInfo::class.java))
        }
    }
}