package com.example.grocerygo.activities_and_frags

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.android.volley.Response
import com.example.grocerygo.R
import com.example.grocerygo.extras.App
import com.example.grocerygo.activities_and_frags.Inheritables.GGToolbarActivity
import com.example.grocerygo.extras.Requester
import com.example.grocerygo.extras.easyToast
import com.example.grocerygo.models.received.ReceivedAddressesObject
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
            if (App.sm.user != null &&
                App.sm.primaryAddress != null &&
                App.sm.displayPayment != null
            ) {
                startActivity(Intent(this, ActivityOrderReview::class.java))
            } else {
                this.easyToast("Please complete the form")
            }
        }
        frame_profile.setOnClickListener {
            val intent = Intent(this, ActivityHost::class.java)
            intent.putExtra(ActivityHost.KEY_TAB_ID, ActivityHost.TabEnum.Profile.id)
            startActivity(intent)
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
        text_view_address_value.text =
            App.sm.primaryAddress?.displayableStreetAddress ?: "Primary address not selected"
        text_view_payment_value.text = App.sm.displayPayment ?: "Payment method not selected"

        if(App.sm.user?.name == null) {
            image_view_profile_good.visibility = View.GONE
            image_view_profile_bad.visibility = View.VISIBLE
        } else {
            image_view_profile_good.visibility = View.VISIBLE
            image_view_profile_bad.visibility = View.GONE
        }
        if(App.sm.primaryAddress == null) {
            image_view_address_good.visibility = View.GONE
            image_view_address_bad.visibility = View.VISIBLE
        } else {
            image_view_address_good.visibility = View.VISIBLE
            image_view_address_bad.visibility = View.GONE
        }
        if(App.sm.displayPayment == null) {
            image_view_payment_good.visibility = View.GONE
            image_view_payment_bad.visibility = View.VISIBLE
        } else {
            image_view_payment_good.visibility = View.VISIBLE
            image_view_payment_bad.visibility = View.GONE
        }
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