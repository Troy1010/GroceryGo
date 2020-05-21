package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.inheritables.GGToolbarActivity
import kotlinx.android.synthetic.main.activity_c_thanks.*

class ActivityThankYou: GGToolbarActivity() {
    override val title: String
        get() = "Thank you"
    override val layout: Int
        get() = R.layout.activity_c_thanks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_thanks_back_to_shopping_send.setOnClickListener {
            startActivity(Intent(this, ActivityHost::class.java))
        }
    }
}