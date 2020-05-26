package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.extras.App
import kotlinx.android.synthetic.main.activity_payment.*

class ActivityPayment: GGToolbarActivity(layout = R.layout.activity_payment) {
    override val title = "Payment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_payment_submit.setOnClickListener {
            App.sm.displayPayment = hidePayment(text_input_card_number.text.toString())
            startActivity(Intent(this, ActivityPaymentInfo::class.java))
        }
    }
}

fun hidePayment(s: String):String? {
    return if (s.length < 4) {
        null
    } else {
        "**** **** **** ${s.takeLast(4)}"
    }
}