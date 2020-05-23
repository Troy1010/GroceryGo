package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import kotlinx.android.synthetic.main.activity_c_thanks.*

class ActivityThankYou: GGToolbarActivity(layout = R.layout.activity_c_thanks) {
    override val title: String
        get() = "Thank you"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_thanks_back_to_shopping_send.setOnClickListener {
            val intent = Intent(this, ActivityHost::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}