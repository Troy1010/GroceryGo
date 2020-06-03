package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import com.example.grocerygo.R
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.extras.App
import kotlinx.android.synthetic.main.activity_thank_you.*

class ActivityThankYou : GGToolbarActivity(layout = R.layout.activity_thank_you) {
    override val title = "Order Complete"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
        refresh()
//        ${App.sm.user?.name}
    }

    private fun refresh() {
        text_view_thank_you.text = "Thank you, ${App.sm.user?.name}"
    }

    private fun setupListeners() {
        button_thanks_back_to_shopping_send.setOnClickListener {
            val intent = Intent(this, ActivityHost::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}