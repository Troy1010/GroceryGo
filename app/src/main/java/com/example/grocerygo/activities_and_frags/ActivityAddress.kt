package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.inheritables.GGToolbarActivity
import kotlinx.android.synthetic.main.activity_c_address.*

class ActivityAddress: GGToolbarActivity() {
    override val title: String
        get() = "Update Address"
    override val layout: Int
        get() = R.layout.activity_c_address

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_address_submit.setOnClickListener {
            startActivity(Intent(this, ActivityPaymentInfo::class.java))
        }
    }
}