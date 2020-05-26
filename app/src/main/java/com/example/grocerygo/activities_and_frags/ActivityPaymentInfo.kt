package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.android.volley.Response
import com.example.grocerygo.R
import com.example.grocerygo.adapters.AdapterRecyclerView
import com.example.grocerygo.extras.App
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.extras.Requester
import com.example.grocerygo.extras.logz
import com.example.grocerygo.models.*
import com.example.grocerygo.models.received.ReceivedAddressesObject
import com.example.grocerygo.models.received.ReceivedLoginObject
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_payment_info.*

class ActivityPaymentInfo : GGToolbarActivity(layout = R.layout.activity_payment_info) {
    override val title = "Payment Info"

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun setupListeners() {
        button_payment_info_send.setOnClickListener {
            startActivity(Intent(this, ActivityOrderReview::class.java))
        }
        frame_address.setOnClickListener {
            Requester.requestAddresses(
                App.sm.user?._id, Response.Listener {
                    val receivedAddressesObject = GsonBuilder().create()
                        .fromJson(it.toString(), ReceivedAddressesObject::class.java)
                    // if we have at least 1, bring to ActivityAddresses, otherwise ActivityAddress
                    if (receivedAddressesObject.data.isEmpty()) {
                        startActivity(Intent(this, ActivityAddress::class.java))
                    } else {
                        startActivity(Intent(this, ActivityAddresses::class.java))
                    }
                }
            )
        }
        frame_payment.setOnClickListener {
            startActivity(Intent(this, ActivityPayment::class.java))
        }
    }

    fun refresh() {
        text_view_profile_value.text = App.sm.user?.name ?: "User not logged in"
        text_view_address_value.text = App.sm.primaryAddress?.displayableStreetAddress ?: "Primary address not selected"
        text_view_payment_value.text = "" // TODO
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
    }

    // Setup Toolbar
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.menu_cart).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }
}